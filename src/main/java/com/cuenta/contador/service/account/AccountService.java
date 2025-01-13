package com.cuenta.contador.service.account;
import com.cuenta.contador.service.account.Account.AccountID;

public interface AccountService {
    Account getAccount(AccountID id);

    void createAccount(Account account);
}
