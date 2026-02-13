package com.cuenta.contador.service.budget;

import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.service.user.UserContext;
import com.cuenta.contador.service.budget.Budget.BudgetID;
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
  public void storeBudget(Budget budget){
    UserID userId = UserContext.getUserID();
    budgetStore.storeBudget(userId, budget);
  }

  @Override
  public void storeBudgetAllocations(BudgetID budgetId, List<BudgetAllocation> budgetAllocations){
    budgetStore.storeBudgetAllocations(budgetId, budgetAllocations);
  }

  @Override
  public Budget getBudget(int year, byte month){
    UserID userId = UserContext.getUserID();
    return budgetStore.getBudget(userId, year, month);
  }

  @Override
  public Double getBudgetSpent(BudgetID budgetId){
    UserID userId = UserContext.getUserID();
    return budgetStore.getBudgetSpent(userId, budgetId);
  }

  @Override
  public List<BudgetAllocation> getBudgetAllocations(Budget.BudgetID budgetId){
    return budgetStore.getBudgetAllocations(budgetId);
  }

  @Override
  public List<Budget> getAllBudgets(){
    UserID userId = UserContext.getUserID();
    return budgetStore.getAllBudgets(userId);
  }

  @Override
  public BudgetID getCurrentBudgetId(){
    UserID userId = UserContext.getUserID();
    return  budgetStore.getCurrentBudgetId(userId);
  }

  @Override
  public boolean budgetCategoryExists(String category){
    UserID userId = UserContext.getUserID();
    return budgetStore.budgetCategoryExists(userId, category);
  }

  @Override
  public void updateBudgetAllocation(BudgetID budgetId, String category, double transactionAmount){
    System.out.println("update budget allocation triggered");
    budgetStore.updateBudgetAllocation(budgetId, category, transactionAmount);
  }
}
