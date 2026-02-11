package com.cuenta.contador.server.json.budget;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BudgetJson {
  @JsonProperty
  private Integer id;

  @JsonProperty
  private Integer userId;

  @JsonProperty
  private Integer year;

  @JsonProperty
  private byte monthNumber;

  @JsonProperty
  private Double totalAmount;

  @JsonProperty
  private List<BudgetAllocationJson> budgetAllocationJsonList;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public byte getMonthNumber() {
    return monthNumber;
  }

  public void setMonthNumber(byte monthNumber) {
    this.monthNumber = monthNumber;
  }

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public List<BudgetAllocationJson> getBudgetAllocationJsonList() {
    return budgetAllocationJsonList;
  }

  public void setBudgetAllocationJsonList(List<BudgetAllocationJson> budgetAllocationJsonList) {
    this.budgetAllocationJsonList = budgetAllocationJsonList;
  }
}
