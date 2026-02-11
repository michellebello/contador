package com.cuenta.contador.service.budget;

import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.service.user.UserContext;
import com.cuenta.contador.store.budgets.BudgetStore;
import jakarta.inject.Inject;

public class BudgetServiceImpl implements  BudgetService{
  private final BudgetStore budgetStore;
  @Inject
  public BudgetServiceImpl(BudgetStore budgetStore){
    this.budgetStore = budgetStore;
  }

  @Override
  public void storeBudget(Budget budget){
    UserID userId = UserContext.getUserID();
    budgetStore.storeBudget(userId, budget);
  }

  @Override
  public Budget getBudget(int year, byte month){
    UserID userId = UserContext.getUserID();
    return budgetStore.getBudget(userId, year, month);
  }
}
