package com.cuenta.contador.service.budget;

public class BudgetAllocation {
  private Budget.BudgetID budgetId;
  private String category;
  private Double amount;

  public BudgetAllocation(Budget.BudgetID budgetId, String category, Double amount) {
    this.budgetId = budgetId;
    this.category = category;
    this.amount = amount;
  }

  public Budget.BudgetID getBudgetId() {
    return budgetId;
  }

  public void setBudgetId(Budget.BudgetID budgetId) {
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
}
