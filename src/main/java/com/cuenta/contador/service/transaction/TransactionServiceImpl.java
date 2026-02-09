package com.cuenta.contador.service.transaction;

import com.cuenta.contador.service.account.AccountService;
import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.service.user.UserContext;
import com.cuenta.contador.store.transaction.TransactionStore;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;

import java.time.LocalDate;
import java.util.List;
import jakarta.inject.Inject;

public class TransactionServiceImpl implements TransactionService{
    private final AccountService accountService;
    private final TransactionStore transactionStore;

    @Inject
    public TransactionServiceImpl(TransactionStore transactionStore, AccountService accountService){
        this.transactionStore = transactionStore;
        this.accountService = accountService;
    }


    @Override
    public void storeTransaction(Transaction transaction){
        UserID userId = UserContext.getUserID();
        System.out.println("transaction isTaxable sent  " + transaction.getIsTaxable());
        accountService.updateAccountBalance(transaction.getAccountId(), transaction.getAmount(), transaction.getTypeName());
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
    public void updateTransaction(TransactionID transactionId, Transaction transaction){
        UserID userId = UserContext.getUserID();
        if (transaction.getAmount() != null){
            double prev = transactionStore.getTransactionAmount(userId, transactionId);
            String type = transactionStore.getTransactionType(userId, transactionId);
            double difference = (prev - transaction.getAmount());
            if (type.equals("Expense")){
              if (difference >= 0) {
                  difference *=-1;
                  accountService.updateAccountBalance(transaction.getAccountId(), difference, type);
              } else {
                accountService.updateAccountBalance(transaction.getAccountId(), difference, type);
              }
            } else {

            }


        }
        transactionStore.updateTransaction(userId, transactionId, transaction);
    }
    @Override
    public void deleteTransaction(TransactionID transactionId){
        UserID userId = UserContext.getUserID();
        transactionStore.deleteTransaction(userId, transactionId);
    }
}
