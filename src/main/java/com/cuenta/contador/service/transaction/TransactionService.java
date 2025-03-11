package com.cuenta.contador.service.transaction;

import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    void storeTransaction(Transaction transaction);

    Transaction getTransaction(TransactionID id);

    public List<Transaction> getTransactions(List<TransactionID> ids, LocalDate after, LocalDate before);

    void updateTransaction(TransactionID transactionId, Transaction transaction);
    void deleteTransaction(TransactionID transactionId);

}
