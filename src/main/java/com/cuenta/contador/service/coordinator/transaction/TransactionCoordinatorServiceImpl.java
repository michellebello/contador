package com.cuenta.contador.service.coordinator.transaction;

import com.cuenta.contador.service.account.AccountService;
import com.cuenta.contador.service.budget.Budget;
import com.cuenta.contador.service.budget.Budget.BudgetID;
import com.cuenta.contador.service.budget.BudgetService;
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
    BudgetID currBudgetId = budgetService.getCurrentBudgetId();
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
    Transaction oldTransaction = transactionService.getTransaction(transactionId);

    if (transaction.getAmount() != null){
      double difference = oldTransaction.getAmount() - transaction.getAmount();
      accountService.updateAccountBalanceFromUpdate(transaction.getAccountId(), difference, transaction.getTypeName());
      if (transaction.getTypeName().equals("Expense")){
        BudgetID currBudgetId = budgetService.getCurrentBudgetId();
        if (currBudgetId != null){
          double adjustment = -difference;
          if (budgetService.budgetCategoryExists(currBudgetId, oldTransaction.getCategory())){
            budgetService.updateBudgetAllocation(currBudgetId, oldTransaction.getCategory(), adjustment);
            budgetService.updateBudgetSpent(currBudgetId, adjustment);
          }
        }
      }
    }
    transactionService.updateTransaction(userId, transactionId, transaction);
  }

  public void deleteTransaction(TransactionID transactionId){
    User.UserID userId = UserContext.getUserID();
    Transaction oldTransaction = transactionService.getTransaction(transactionId);
    accountService.updateAccountBalanceFromDelete(transactionService.getTransactionAccountId(userId, transactionId), oldTransaction.getAmount(), transactionService.getTransactionType(userId, transactionId));
    if (oldTransaction.getTypeName().equals("Expense")) {
      BudgetID currBudgetId = budgetService.getCurrentBudgetId();
      if (currBudgetId != null && budgetService.budgetCategoryExists(currBudgetId, oldTransaction.getCategory())) {
        budgetService.updateBudgetAllocation(currBudgetId, oldTransaction.getCategory(), -oldTransaction.getAmount());
        budgetService.updateBudgetSpent(currBudgetId, -oldTransaction.getAmount());
      }
    }
    transactionService.deleteTransaction(userId, transactionId);
  }
}
