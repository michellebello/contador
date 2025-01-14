package com.cuenta.contador.server.serializer.account;
import com.cuenta.contador.server.json.account.AccountJson;
import com.cuenta.contador.service.account.Account;
import com.cuenta.contador.service.account.Account.AccountID;

public class AccountSerializer {
    public Account fromAccountJson(AccountJson accountJson){
        return new Account(
                accountJson.getId() == null ? null : AccountID.of(accountJson.getId()),
                accountJson.getName(),
                accountJson.getNumber(),
                accountJson.getType(),
                accountJson.getBalance()
        );
    }

    public AccountJson toAccountJson(Account account) {
        AccountJson json = new AccountJson();
        json.setId(account.getId().getIntId());
        json.setType(account.getType());
        json.setBalance(account.getBalance());
        json.setName(account.getName());
        json.setNumber(account.getNumber());
        return json;
    }
}

