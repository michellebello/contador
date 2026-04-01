package com.cuenta.contador.server.json.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaginatedTransactionJson {
  @JsonProperty
  List<TransactionJson> transactionJsonList;
  @JsonProperty
  Integer cursor;

  @JsonProperty
  boolean hasMore;

  public List<TransactionJson> getTransactionJsonList() {
    return transactionJsonList;
  }

  public void setTransactionJsonList(List<TransactionJson> transactionJsonList) {
    this.transactionJsonList = transactionJsonList;
  }

  public Integer getCursor() {
    return cursor;
  }

  public void setCursor(Integer cursor) {
    this.cursor = cursor;
  }

  public boolean getHasMore() {
    return hasMore;
  }

  public void setHasMore(boolean hasMore) {
    this.hasMore = hasMore;
  }
}
