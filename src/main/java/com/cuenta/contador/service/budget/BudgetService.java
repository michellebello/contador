package com.cuenta.contador.service.budget;

import com.cuenta.contador.service.budget.Budget.BudgetID;
import java.util.List;

public interface BudgetService {
  void storeBudget(Budget budget);
  void storeBudgetAllocations(BudgetID budgetId, List<BudgetAllocation> budgetAllocations);
  Budget getBudget(int year, byte month);
  List<BudgetAllocation> getBudgetAllocations(Budget.BudgetID budgetID);
}
