package com.cuenta.contador.server.json.budget;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BudgetAllocationJson {
  @JsonProperty
  private String category;

  @JsonProperty
  private Double amount;

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

}
