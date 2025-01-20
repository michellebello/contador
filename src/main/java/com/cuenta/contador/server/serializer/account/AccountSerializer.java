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

    // for patch methods, we might have some null entries
    public Account fromPartialAccountJson(AccountJson accountJson, Account currAccount){
        return new Account(
                currAccount.getId(),
                accountJson.getName() != null ? accountJson.getName() : currAccount.getName(),
                accountJson.getNumber() != null? accountJson.getNumber() : currAccount.getNumber(),
                accountJson.getType() != null? accountJson.getType() : currAccount.getType(),
                accountJson.getBalance() != null  ? accountJson.getBalance() : currAccount.getBalance()
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

