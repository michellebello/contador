package com.cuenta.contador.service.transaction;

import java.util.List;

public class PaginatedTransaction {
  public List<Transaction> transactionList;
  public Integer cursor;
  public Boolean hasMore;

  public PaginatedTransaction(List<Transaction> transactionList, Integer cursor, Boolean hasMore) {
    this.transactionList = transactionList;
    this.cursor = cursor;
    this.hasMore = hasMore;
  }

  public List<Transaction> getTransactionList() {
    return transactionList;
  }

  public void setTransactionList(List<Transaction> transactionList) {
    this.transactionList = transactionList;
  }

  public Integer getCursor() {
    return cursor;
  }

  public void setCursor(Integer cursor) {
    this.cursor = cursor;
  }

  public Boolean getHasMore() {
    return hasMore;
  }

  public void setHasMore(Boolean hasMore) {
    this.hasMore = hasMore;
  }
}
