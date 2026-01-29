package com.cuenta.contador.service.account;

import java.util.Objects;

public class WithAccountNumber<T> {
  T obj;
  String accountNumber;

  public WithAccountNumber(T obj, String accountNumber) {
    this.obj = obj;
    this.accountNumber = accountNumber;
  }

  public T getObj() {
    return obj;
  }

  public void setObj(T obj) {
    this.obj = obj;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof WithAccountNumber<?> that)) return false;
    return Objects.equals(obj, that.obj) && Objects.equals(accountNumber, that.accountNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(obj, accountNumber);
  }

  @Override
  public String toString() {
    return "WithAccountNumber{" +
      "obj=" + obj +
      ", accountNumber='" + accountNumber + '\'' +
      '}';
  }
}
