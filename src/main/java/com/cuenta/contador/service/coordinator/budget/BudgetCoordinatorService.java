package com.cuenta.contador.service.coordinator.budget;

import com.cuenta.contador.service.budget.Budget;
import com.cuenta.contador.service.budget.Budget.BudgetID;
import com.cuenta.contador.service.budget.BudgetAllocation;

import java.util.List;

public interface BudgetCoordinatorService {
  BudgetID createAndProcessNewBudget(Budget budget) throws Exception;
  void storeBudgetAllocations(BudgetID budgetId, List<BudgetAllocation> budgetAllocations) throws Exception;
}
