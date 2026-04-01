package com.cuenta.contador.service.coordinator.budget;

import com.cuenta.contador.service.budget.Budget;
import com.cuenta.contador.service.budget.Budget.BudgetID;
import com.cuenta.contador.service.budget.BudgetAllocation;
import com.cuenta.contador.service.budget.BudgetService;
import com.cuenta.contador.service.transaction.Transaction;
import com.cuenta.contador.service.transaction.TransactionService;
import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.service.user.UserContext;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BudgetCoordinatorServiceImpl implements BudgetCoordinatorService{
  private final TransactionService transactionService;
  private final BudgetService budgetService;

  @Inject
  public BudgetCoordinatorServiceImpl(TransactionService transactionService, BudgetService budgetService){
    this.transactionService = transactionService;
    this.budgetService = budgetService;
  }

  @Override
  public BudgetID createAndProcessNewBudget(Budget budget) throws Exception {
    UserID userId = UserContext.getUserID();
    return budgetService.storeBudget(userId, budget);
  }

  @Override
  public void storeBudgetAllocations(BudgetID budgetId, List<BudgetAllocation> budgetAllocations) throws Exception {
    budgetService.storeBudgetAllocations(budgetId, budgetAllocations);

    Set<String> newlyAddedCategories = budgetAllocations.stream()
      .map(BudgetAllocation::getCategory)
      .collect(Collectors.toSet());

    Budget budget = budgetService.getBudgetFromId(budgetId);
    LocalDate monthStart = LocalDate.of(budget.getYear(), budget.getMonthNumber(), 1);
    LocalDate today = LocalDate.now();

    List<Transaction> prevTransactions = transactionService.getTransactions(List.of(), monthStart, today);

    for (Transaction t : prevTransactions) {
      String category = t.getCategory();
      if (newlyAddedCategories.contains(category)) {
        budgetService.updateBudgetAllocation(budgetId, category, t.getAmount());
        budgetService.updateBudgetSpent(budgetId, t.getAmount());
      }
    }
  }
}
