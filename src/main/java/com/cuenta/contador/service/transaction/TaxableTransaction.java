package com.cuenta.contador.service.transaction;

import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;

import java.time.LocalDateTime;

public class TaxableTransaction {
  private TransactionID id;
  private AccountID accountId;
  private String accountNumber;
  private String name;
  private String note;
  private String category;
  private String typeName;
  private Double amount;
  private LocalDateTime createdOn;

  public TaxableTransaction(TransactionID id, AccountID accountId, String accountNumber, String name, String note, String category, String typeName, Double amount, LocalDateTime createdOn) {
    this.id = id;
    this.accountId = accountId;
    this.accountNumber = accountNumber;
    this.name = name;
    this.note = note;
    this.category = category;
    this.typeName = typeName;
    this.amount = amount;
    this.createdOn = createdOn;
  }

  public TransactionID getId() {
    return id;
  }

  public void setId(Transaction.TransactionID id) {
    this.id = id;
  }

  public AccountID getAccountId() {
    return accountId;
  }

  public void setAccountId(AccountID accountId) {
    this.accountId = accountId;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }
}
