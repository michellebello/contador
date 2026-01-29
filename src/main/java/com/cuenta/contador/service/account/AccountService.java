package com.cuenta.contador.service.account;
import com.cuenta.contador.service.account.Account.AccountID;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountService {
    Account getAccount(AccountID id);

    List<Account> getAccounts(List<AccountID> ids);

    List<String> getAccountNumbers();

    Map<String, BigDecimal> getBalanceByAccountType();

    boolean accountNumberExists(String accountNumber);
    void createAccount(Account account);
    void deleteAccount(AccountID id);
    void updateAccount(AccountID accountId, Account account);
    void updateAccountBalance(AccountID accountId, Double amount);
}
