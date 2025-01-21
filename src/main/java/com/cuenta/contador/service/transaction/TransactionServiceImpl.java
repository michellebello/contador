package com.cuenta.contador.service.transaction;

import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.service.user.UserContext;
import com.cuenta.contador.store.transaction.TransactionStore;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;
import java.util.List;
import jakarta.inject.Inject;

public class TransactionServiceImpl implements TransactionService{
    private final TransactionStore transactionStore;

    @Inject
    public TransactionServiceImpl(TransactionStore transactionStore){
        this.transactionStore = transactionStore;
    }

    @Override
    public void storeTransaction(Transaction transaction){
        UserID userId = UserContext.getUserID();
        transactionStore.storeTransaction(userId,transaction);
    }

    @Override
    public Transaction getTransaction(TransactionID id){
        UserID userId = UserContext.getUserID();
        return transactionStore.getTransaction(userId, id);
    }

    @Override
    public List<Transaction> getTransactions(List<TransactionID> ids){
        UserID userId = UserContext.getUserID();
        return transactionStore.getTransactions(userId, ids);
    }

    @Override
    public List<Transaction> getTransactionsByAccount(AccountID accountId){
        UserID userId = UserContext.getUserID();
        return transactionStore.getTransactionsByAccount(userId, accountId);
    }

    @Override
    public void updateTransaction(TransactionID transactionId, Transaction transaction){
        UserID userId = UserContext.getUserID();
        transactionStore.updateTransaction(userId, transactionId, transaction);
    }
    @Override
    public void deleteTransaction(TransactionID transactionId){
        UserID userId = UserContext.getUserID();
        transactionStore.deleteTransaction(userId, transactionId);
    }
}
