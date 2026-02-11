package com.cuenta.contador.service.budget;

import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.service.user.UserContext;
import com.cuenta.contador.store.budgets.BudgetStore;
import jakarta.inject.Inject;

import java.util.List;

public class BudgetServiceImpl implements  BudgetService{
  private final BudgetStore budgetStore;
  @Inject
  public BudgetServiceImpl(BudgetStore budgetStore){
    this.budgetStore = budgetStore;
  }

  @Override
  public void storeBudget(Budget budget, List<BudgetAllocation> allocations){
    UserID userId = UserContext.getUserID();
    budgetStore.storeBudget(userId, budget, allocations);
  }

  @Override
  public Budget getBudget(int year, byte month){
    UserID userId = UserContext.getUserID();
    return budgetStore.getBudget(userId, year, month);
  }
}
