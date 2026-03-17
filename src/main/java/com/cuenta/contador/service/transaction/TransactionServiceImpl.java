package com.cuenta.contador.service.transaction;

import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.service.user.UserContext;
import com.cuenta.contador.store.transaction.TransactionStore;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;

import java.time.LocalDate;
import java.util.List;
import jakarta.inject.Inject;

public class TransactionServiceImpl implements TransactionService{
    private final TransactionStore transactionStore;

    @Inject
    public TransactionServiceImpl(TransactionStore transactionStore){
        this.transactionStore = transactionStore;
    }


    @Override
    public void storeTransaction(UserID userId, Transaction transaction){
        transactionStore.storeTransaction(userId, transaction);
    }

    @Override
    public Transaction getTransaction(TransactionID id){
        UserID userId = UserContext.getUserID();
        return transactionStore.getTransaction(userId, id);
    }

    @Override
    public List<Transaction> getTransactions(List<TransactionID> ids, LocalDate after, LocalDate before){
        UserID userId = UserContext.getUserID();
        return transactionStore.getTransactions(userId, ids, after, before);
    }

    @Override
    public List<TaxableTransaction> getTaxableTransactions(LocalDate startDate, LocalDate endDate, String category){
        UserID userId = UserContext.getUserID();
        return transactionStore.getTaxableTransactions(userId, startDate, endDate, category);
    }

    @Override
    public void addTransactionNote(TaxableTransactionNoteUpdate noteUpdate){
        UserID userId = UserContext.getUserID();
        transactionStore.addTransactionNote(userId, noteUpdate);
    }

    @Override
    public void updateTransaction(UserID userId, TransactionID transactionId, Transaction transaction){
        transactionStore.updateTransaction(userId, transactionId, transaction);
    }

    @Override
    public double getTransactionAmount(UserID userId, TransactionID transactionId){
        return transactionStore.getTransactionAmount(userId, transactionId);
    }

    @Override
    public String getTransactionType(UserID userId, TransactionID transactionId){
        return transactionStore.getTransactionType(userId, transactionId);
    }
    @Override
    public void deleteTransaction(UserID userId, TransactionID transactionId){
        transactionStore.deleteTransaction(userId, transactionId);
    }

    @Override
    public AccountID getTransactionAccountId(UserID userId, TransactionID transactionId){
        return transactionStore.getTransactionAccountId(userId, transactionId);
    }
}
