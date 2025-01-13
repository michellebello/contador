package com.cuenta.contador.service.account;

import com.cuenta.contador.store.account.AccountStore;
import com.cuenta.contador.service.account.Account.AccountID;

import javax.inject.Inject;

public class AccountServiceImpl implements AccountService {
    private final AccountStore accountStore;

    @Inject
    public AccountServiceImpl(AccountStore accountStore){
        this.accountStore = accountStore;
    }

    @Override
    public Account getAccount(AccountID id){
        return accountStore.getAccount(id);
    }

    @Override
    public void createAccount(Account account){
        accountStore.storeAccount(account);
    }


}
