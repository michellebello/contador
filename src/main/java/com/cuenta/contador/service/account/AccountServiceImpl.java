package com.cuenta.contador.service.account;

import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.service.user.UserContext;
import com.cuenta.contador.store.account.AccountStore;
import com.cuenta.contador.service.account.Account.AccountID;

import jakarta.inject.Inject;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final AccountStore accountStore;

    @Inject
    public AccountServiceImpl(AccountStore accountStore){
        this.accountStore = accountStore;
    }

    @Override
    public Account getAccount(AccountID id){
        UserID userId = UserContext.getUserID();
        return accountStore.getAccount(userId, id);
    }

    @Override
    public List<Account> getAccounts(List<AccountID> ids) {
        UserID userId = UserContext.getUserID();
        return accountStore.getAccounts(userId, ids);
    }

    @Override
    public void createAccount(Account account){
        // in debug mode stop
        UserID userId = UserContext.getUserID();
        accountStore.storeAccount(userId, account);
    }

    @Override
    public void deleteAccount(AccountID id){
        UserID userId = UserContext.getUserID();
        accountStore.deleteAccount(userId, id);
    }


}
