package com.cuenta.contador.server.json.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

public class TaxableTransactionJson {
  @JsonProperty
  private Integer id;
  @JsonProperty
  private Integer accountId;
  @JsonProperty
  private String accountNumber;
  @JsonProperty
  private String name;
  @JsonProperty
  @Nullable
  private String note;
  @JsonProperty
  private String typeName;
  @JsonProperty
  private String category;
  @JsonProperty
  private Double amount;
  @JsonProperty
  private LocalDateTime createdOn;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
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

  @Nullable
  public String getNote() {
    return note;
  }

  public void setNote(@Nullable String note) {
    this.note = note;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
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

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }
}
