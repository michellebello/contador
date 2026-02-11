package com.cuenta.contador.service.budget;

import java.util.List;

public interface BudgetService {
  void storeBudget(Budget budget, List<BudgetAllocation> allocations);
  Budget getBudget(int year, byte month);
}
