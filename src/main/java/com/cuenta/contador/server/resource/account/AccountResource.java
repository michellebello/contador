package com.cuenta.contador.server.resource.account;

import com.cuenta.contador.server.json.account.AccountJson;
import com.cuenta.contador.server.serializer.account.AccountSerializer;
import com.cuenta.contador.service.account.Account;
import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.account.AccountService;

import com.cuenta.contador.service.transaction.Transaction;
import com.cuenta.contador.service.user.UserContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.IntegrityConstraintViolationException;

import java.lang.reflect.AnnotatedArrayType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Path("accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {
    private static final String ID = "id";

    private static final String ACCOUNT_ID = "accountId";
    private final AccountService accountService;
    private final AccountSerializer accountSerializer;

    @Inject
    public AccountResource(AccountService accountService, AccountSerializer accountSerializer){
        this.accountService = accountService;
        this.accountSerializer = accountSerializer;
    }

    @GET
    @Path("{" + ACCOUNT_ID + "}")
    public Response getAccount(@PathParam(ACCOUNT_ID) int accountId){
        Account account = accountService.getAccount(AccountID.of(accountId));
        if (account != null){
            AccountJson accountJson = accountSerializer.toAccountJson(account);
            return Response.ok(accountJson).build();
        } else {
            String message = "Account with id " + accountId + " does not exist";
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public List<AccountJson> getAccounts() {
        List<Account> accounts = accountService.getAccounts(List.of());
        System.out.println("getAccounts: Query executed successfully, fetched " + accounts.size() + " accounts");
            return accounts.stream()
                .map(accountSerializer::toAccountJson)
                .toList();
    }

    @GET
    @Path("/numbers")
    public List<AccountJson> getAccountNumbers() {
        List<Account> accounts = accountService.getAccounts(List.of());
        return accounts.stream()
                .map(account -> {
                    AccountJson accountJson = new AccountJson();
                    accountJson.setId(account.getId().getIntId());
                    accountJson.setNumber(account.getNumber());
                    return accountJson;
                })
                .toList();
    }

    @GET
    @Path("/balance")
    public Map<String, BigDecimal> getAccountTypeBalance(){
        return accountService.getBalanceByAccountType();
    }


    @POST
    public Response createAccount(AccountJson accountJson) {
        if (accountJson == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Empty body").build();
        }
        Account account = accountSerializer.fromAccountJson(accountJson);
        if (accountService.accountNumberExists(account.getNumber())) {
            return Response.status(Response.Status.CONFLICT).entity("Account number already exists").build();
        }
        accountService.createAccount(account);
        return Response.ok().entity("New account added").build();
    }

    @PATCH
    @Path("{" + ACCOUNT_ID + "}")
    public Response updateAccount(@PathParam(ACCOUNT_ID) int accountId, AccountJson accountJson){
        if (accountJson == null){
            Response.status(Response.Status.BAD_REQUEST).entity("Account cannot be updated without any data, please provide data to update transaction.").build();
        }
        Account currAcount = accountService.getAccount(AccountID.of(accountId));
        if (currAcount == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Account with id " + accountId + " not found").build();
        }
      assert accountJson != null;
      Account accountToUpdate = accountSerializer.fromPartialAccountJson(accountJson, currAcount);
        try {
            accountService.updateAccount(AccountID.of(accountId), accountToUpdate);
            return Response.ok("Account successfully updated with new data").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Server error try again").build();
        }

    }

    @DELETE
    @Path("{" + ACCOUNT_ID + "}")
    public Response deleteAccount(@PathParam(ACCOUNT_ID) int accountId){
        Account accountToDelete = accountService.getAccount(AccountID.of(accountId));
        if (accountToDelete == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Account with id " + accountId + " not found").build();
        }
        try {
            accountService.deleteAccount(AccountID.of(accountId));
            return Response.ok("account deleted").build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Server error try again").build();
        }
    }
}
