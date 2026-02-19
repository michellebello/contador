package com.cuenta.contador.service.budget;
import com.cuenta.contador.service.budget.Budget.BudgetID;

public class BudgetAllocation {
  private int id;
  private BudgetID budgetId;
  private String category;
  private Double amount;
  private Double spent;

  public BudgetAllocation(int id, BudgetID budgetId, String category, Double amount, Double spent) {
    this.id = id;
    this.budgetId = budgetId;
    this.category = category;
    this.amount = amount;
    this.spent = spent;
  }
  public BudgetAllocation(int id, String category, Double amount, Double spent) {
    this.id = id;
    this.category = category;
    this.amount = amount;
    this.spent = spent;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Budget.BudgetID getBudgetId() {
    return budgetId;
  }

  public void setBudgetId(BudgetID budgetId) {
    this.budgetId = budgetId;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Double getSpent() {
    return spent;
  }

  public void setSpent(Double spent){
    this.spent = spent;
  }
}
