package com.cuenta.contador.service.account;

import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.service.user.UserContext;
import com.cuenta.contador.store.account.AccountStore;
import com.cuenta.contador.service.account.Account.AccountID;

import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
    public List<AccountNumber> getAccountNumbers() {
        UserID userId = UserContext.getUserID();
        return accountStore.getAccountNumbers(userId);
    }
    @Override
    public Map<String, BigDecimal> getBalanceByAccountType() {
        UserID userId = UserContext.getUserID();
        return accountStore.getBalanceByAccountType(userId);
    }

    @Override
    public boolean accountNumberExists(String accountNumber){
        UserID userId = UserContext.getUserID();
        return accountStore.accountNumberExists(userId, accountNumber);
    }

    @Override
    public void createAccount(Account account){
        UserID userId = UserContext.getUserID();
        accountStore.storeAccount(userId, account);
    }

    @Override
    public void updateAccount(AccountID accountId, Account account){
        UserID userId = UserContext.getUserID();
        accountStore.updateAccount(userId, accountId, account);
    }

    @Override
    public void deleteAccount(AccountID id){
        UserID userId = UserContext.getUserID();
        accountStore.deleteAccount(userId, id);
    }

    @Override
    public void updateAccountBalanceFromUpdate(AccountID accountId, Double amount, String transactionType){
        UserID userId = UserContext.getUserID();
        accountStore.updateAccountBalanceFromUpdate(userId, accountId, amount, transactionType);
    }

    @Override
    public void updateAccountBalanceFromDelete(AccountID accountId, Double amount, String transactionType){
        UserID userId = UserContext.getUserID();
        accountStore.updateAccountBalanceFromDelete(userId, accountId, amount, transactionType);
    }
}
