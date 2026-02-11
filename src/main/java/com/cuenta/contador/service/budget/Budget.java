package com.cuenta.contador.service.budget;

import com.cuenta.contador.infra.ID;
import com.cuenta.contador.service.user.User.UserID;


public class Budget {
  private BudgetID budgetId;
  private UserID userId;
  private int year;
  private byte monthNumber;
  private Double totalAmount;

  public Budget(BudgetID budgetId, UserID userId, int year, byte monthNumber, Double totalAmount) {
    this.budgetId = budgetId;
    this.userId = userId;
    this.year = year;
    this.monthNumber = monthNumber;
    this.totalAmount = totalAmount;
  }

  public BudgetID getBudgetId() {
    return budgetId;
  }

  public void setBudgetId(BudgetID budgetId) {
    this.budgetId = budgetId;
  }

  public UserID getUserId() {
    return userId;
  }

  public void setUserId(UserID userId) {
    this.userId = userId;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
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

  public static class BudgetID extends ID<Budget> {
    public BudgetID(int id){
      super(id);
    }

    public static BudgetID of(int id){
      return new BudgetID(id);
    }
  }
}
