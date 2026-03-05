package com.cuenta.contador.service.coordinator.transaction;

import com.cuenta.contador.service.account.AccountService;
import com.cuenta.contador.service.budget.Budget;
import com.cuenta.contador.service.budget.BudgetService;
import com.cuenta.contador.service.coordinator.transaction.TransactionCoordinatorService;
import com.cuenta.contador.service.transaction.Transaction;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;
import com.cuenta.contador.service.transaction.TransactionService;
import com.cuenta.contador.service.user.User;
import com.cuenta.contador.service.user.UserContext;
import jakarta.inject.Inject;

public class TransactionCoordinatorServiceImpl implements TransactionCoordinatorService {
  private final AccountService accountService;
  private final TransactionService transactionService;
  private final BudgetService budgetService;

  @Inject
  public TransactionCoordinatorServiceImpl(AccountService accountService, TransactionService transactionService, BudgetService budgetService){
    this.accountService = accountService;
    this.transactionService = transactionService;
    this.budgetService = budgetService;
  }

  public void createAndProcessTransaction(Transaction transaction){
    User.UserID userId = UserContext.getUserID();
    String category = transaction.getCategory();

    accountService.updateAccountBalanceFromUpdate(transaction.getAccountId(), transaction.getAmount(), transaction.getTypeName());
    Budget.BudgetID currBudgetId = budgetService.getCurrentBudgetId();
    if (currBudgetId != null){
      boolean categoryExists = budgetService.budgetCategoryExists(currBudgetId, category);
      if (categoryExists){
        budgetService.updateBudgetAllocation(currBudgetId, category, transaction.getAmount());
        budgetService.updateBudgetSpent(currBudgetId, transaction.getAmount());
      }
    }
    transactionService.storeTransaction(userId, transaction);
  }

  public void updateTransaction(TransactionID transactionId, Transaction transaction){
    User.UserID userId = UserContext.getUserID();
    if (transaction.getAmount() != null){
      double prev = transactionService.getTransactionAmount(userId, transactionId);
      String transType = transactionService.getTransactionType(userId, transactionId);
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
    transactionService.updateTransaction(userId, transactionId, transaction);
  }

  public void deleteTransaction(TransactionID transactionId){
    User.UserID userId = UserContext.getUserID();
    Double amountToDelete = transactionService.getTransactionAmount(userId, transactionId);
    accountService.updateAccountBalanceFromDelete(transactionService.getTransactionAccountId(userId, transactionId),
      amountToDelete,
      transactionService.getTransactionType(userId, transactionId));
    transactionService.deleteTransaction(userId, transactionId);
  }
}
