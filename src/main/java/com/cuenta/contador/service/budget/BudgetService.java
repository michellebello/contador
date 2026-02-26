package com.cuenta.contador.service.budget;

import com.cuenta.contador.service.budget.Budget.BudgetID;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BudgetService {
  BudgetID storeBudget(Budget budget) throws Exception;
  void storeBudgetAllocations(BudgetID budgetId, List<BudgetAllocation> budgetAllocations) throws Exception;
  Budget getBudget(int year, byte month);
  Double getBudgetSpent(BudgetID budgetId);
  List<BudgetAllocation> getBudgetAllocations(BudgetID budgetID);
  Map<BudgetID, List<BudgetAllocation>> getAllBudgetAllocations();
  List<Budget> getAllBudgets();
  BudgetID getBudgetId(LocalDateTime createdOn);
  BudgetID getCurrentBudgetId();
  boolean budgetCategoryExists(String category);
  void updateBudgetAllocation(BudgetID budgetId, String category, double transactionAmount);
  void updateBudgetSpent(BudgetID budgetId, Double transactionAmount);
  void updateBudgetAllocationTotal(BudgetID budgetId, Integer allocationId, Double allocationTotal) throws Exception;
  void deleteBudgetAllocation(BudgetID budgetId, Integer allocationId) throws Exception;
  Budget getCurrentBudget();
  boolean budgetExists(BudgetID budgetID);
  void deleteBudget(BudgetID budgetId) throws Exception;
}
