package com.cuenta.contador.service.transaction;

import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;
import com.cuenta.contador.service.user.User.UserID;

import java.util.List;

public interface TransactionService {
    void storeTransaction(Transaction transaction);

    Transaction getTransaction(TransactionID id);

    public List<Transaction> getTransactions(List<TransactionID> ids);

    public List<Transaction> getTransactionsByAccount(AccountID accountId);

    void updateTransaction(TransactionID transactionId, Transaction transaction);
    void deleteTransaction(TransactionID transactionId);
}
