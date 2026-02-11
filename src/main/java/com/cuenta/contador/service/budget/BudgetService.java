package com.cuenta.contador.service.budget;

public interface BudgetService {
  void storeBudget(Budget budget);
  Budget getBudget(int year, byte month);
}
