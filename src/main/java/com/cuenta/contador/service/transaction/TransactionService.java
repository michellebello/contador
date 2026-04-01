package com.cuenta.contador.service.transaction;

import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;
import com.cuenta.contador.service.user.User.UserID;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    void storeTransaction(UserID userId, Transaction transaction);
    Transaction getTransaction(TransactionID id);
    List<Transaction> getTransactions(List<TransactionID> ids, LocalDate after, LocalDate before, int page, int pageSize);
    Map<String, Double> getTransactionBreakdown(LocalDate after, LocalDate before);
    List<TaxableTransaction> getTaxableTransactions(LocalDate startDate, LocalDate endDate, String category);
    void addTransactionNote(TaxableTransactionNoteUpdate noteUpdate);
    void updateTransaction(UserID userId, TransactionID transactionId, Transaction transaction);
    void deleteTransaction(UserID userId, TransactionID transactionId);
    double getTransactionAmount(UserID userId, TransactionID transactionId);
    String getTransactionType(UserID userId, TransactionID transactionId);
    AccountID getTransactionAccountId(UserID userId, TransactionID transactionId);
}
