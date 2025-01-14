package com.cuenta.contador.server.resource.account;

import com.cuenta.contador.server.json.account.AccountJson;
import com.cuenta.contador.server.serializer.account.AccountSerializer;
import com.cuenta.contador.service.account.Account;
import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.account.AccountService;
import com.cuenta.contador.service.user.UserContext;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {
    private static final String ID = "id";
    private final AccountService accountService;
    private final AccountSerializer accountSerializer;

    @Inject
    public AccountResource(AccountService accountService, AccountSerializer accountSerializer){
        this.accountService = accountService;
        this.accountSerializer = accountSerializer;
    }

    @GET
    @Path("{" + ID + "}")
    public AccountJson getAccount(@PathParam(ID) int id){
        Account account = accountService.getAccount(AccountID.of(id));
        return accountSerializer.toAccountJson(account);
    }

    @GET
    public List<AccountJson> getAccounts() {
        List<Account> accounts = accountService.getAccounts(List.of());
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
}
