package com.cuenta.contador.server.json.budget;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BudgetAllocationJson {
  @JsonProperty
  private Integer id;

  @JsonProperty
  private Integer budgetId;

  @JsonProperty
  private String category;

  @JsonProperty
  private Double amount;

  @JsonProperty
  private Double spent;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getBudgetId() {
    return budgetId;
  }

  public void setBudgetId(Integer budgetId) {
    this.budgetId = budgetId;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category){
    this.category = category;
  }

  public Double getAmount(){
    return amount;
  }

  public void setAmount(Double amount){
    this.amount = amount;
  }

  public Double getSpent() {
    return spent;
  }

  public void setSpent(Double spent) {
    this.spent = spent;
  }
}
