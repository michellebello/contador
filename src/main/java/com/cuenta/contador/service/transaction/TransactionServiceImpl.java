package com.cuenta.contador.service.transaction;

import com.cuenta.contador.jooq_auto_generated.tables.Budget;
import com.cuenta.contador.service.account.AccountService;
import com.cuenta.contador.service.budget.BudgetService;
import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.service.user.UserContext;
import com.cuenta.contador.store.transaction.TransactionStore;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;

import java.time.LocalDate;
import java.util.List;
import jakarta.inject.Inject;

public class TransactionServiceImpl implements TransactionService{
    private final AccountService accountService;
    private final BudgetService budgetService;
    private final TransactionStore transactionStore;

    @Inject
    public TransactionServiceImpl(TransactionStore transactionStore, BudgetService budgetService, AccountService accountService){
        this.transactionStore = transactionStore;
        this.budgetService = budgetService;
        this.accountService = accountService;
    }


    @Override
    public void storeTransaction(Transaction transaction){
        UserID userId = UserContext.getUserID();
        String category = transaction.getCategory();
        System.out.println("category is " + category);
        accountService.updateAccountBalanceFromUpdate(transaction.getAccountId(), transaction.getAmount(), transaction.getTypeName());
        if (budgetService.budgetCategoryExists(category)){
            System.out.println("if block triggered");
            budgetService.updateBudgetAllocation(budgetService.getCurrentBudgetId(), category, transaction.getAmount());
        }
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
            String transType = transactionStore.getTransactionType(userId, transactionId);
            double difference = (prev - transaction.getAmount());
            if (transType.equals("Expense")) {
                if (difference >= 0) {
                    difference *= -1;
                    accountService.updateAccountBalanceFromUpdate(transaction.getAccountId(), difference, transType);
                } else {
                    accountService.updateAccountBalanceFromUpdate(transaction.getAccountId(), difference, transType);
                }
            }

        }
        transactionStore.updateTransaction(userId, transactionId, transaction);
    }
    @Override
    public void deleteTransaction(TransactionID transactionId){
        UserID userId = UserContext.getUserID();
        Double amountToDelete = transactionStore.getTransactionAmount(userId, transactionId);
        accountService.updateAccountBalanceFromDelete(transactionStore.getTransactionAccountId(userId, transactionId),
          amountToDelete,
          transactionStore.getTransactionType(userId, transactionId));
        transactionStore.deleteTransaction(userId, transactionId);
    }
}
