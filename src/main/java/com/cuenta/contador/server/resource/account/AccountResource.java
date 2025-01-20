package com.cuenta.contador.server.resource.account;

import com.cuenta.contador.server.json.account.AccountJson;
import com.cuenta.contador.server.serializer.account.AccountSerializer;
import com.cuenta.contador.service.account.Account;
import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.account.AccountService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

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
    public AccountJson getAccount(@PathParam(ACCOUNT_ID) int accountId){
        Account account = accountService.getAccount(AccountID.of(accountId));
        return accountSerializer.toAccountJson(account);
    }

    @GET
    public List<AccountJson> getAccounts() {
        List<Account> accounts = accountService.getAccounts(List.of());
        System.out.println("getAccounts: Query executed successfully, fetched " + accounts.size() + " accounts");
            return accounts.stream()
                .map(accountSerializer::toAccountJson)
                .toList();
    }

    @PUT
    public Response createAccount(AccountJson accountJson) {
        Account account = accountSerializer.fromAccountJson(accountJson);
        accountService.createAccount(account);
        return Response.ok().build();
    }

    @DELETE
    @Path("{" + ACCOUNT_ID + "}")
    public Response deleteAccount(@PathParam(ACCOUNT_ID) int accountId){
        try {
            accountService.deleteAccount(AccountID.of(accountId));
            return Response.ok("account deleted").build();
        } catch (Exception e){
            return Response.status(404).build();
        }
    }
}
