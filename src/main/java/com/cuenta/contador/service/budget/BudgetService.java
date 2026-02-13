package com.cuenta.contador.service.budget;

import com.cuenta.contador.service.budget.Budget.BudgetID;
import com.cuenta.contador.service.user.User;

import java.util.List;

public interface BudgetService {
  void storeBudget(Budget budget);
  void storeBudgetAllocations(BudgetID budgetId, List<BudgetAllocation> budgetAllocations);
  Budget getBudget(int year, byte month);
  Double getBudgetSpent(BudgetID budgetId);
  List<BudgetAllocation> getBudgetAllocations(BudgetID budgetID);
  List<Budget> getAllBudgets();
  BudgetID getCurrentBudgetId();
  boolean budgetCategoryExists(String category);
  void updateBudgetAllocation(BudgetID budgetId, String category, double transactionAmount);
}
