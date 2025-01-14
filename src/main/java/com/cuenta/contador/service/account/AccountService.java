package com.cuenta.contador.service.account;
import com.cuenta.contador.service.account.Account.AccountID;

import java.util.List;

public interface AccountService {
    Account getAccount(AccountID id);

    List<Account> getAccounts(List<AccountID> ids);

    void createAccount(Account account);
}
