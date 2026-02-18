package com.cuenta.contador.service.budget;

public class BudgetAllocation {
  private String category;
  private Double amount;
  private Double spent;

  public BudgetAllocation(String category, Double amount, Double spent) {
    this.category = category;
    this.amount = amount;
    this.spent = spent;
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
