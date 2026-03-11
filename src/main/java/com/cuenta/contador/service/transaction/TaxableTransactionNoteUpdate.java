package com.cuenta.contador.service.transaction;

import com.cuenta.contador.service.transaction.Transaction.TransactionID;

public class TaxableTransactionNoteUpdate {
  private TransactionID id;
  private String note;

  public TaxableTransactionNoteUpdate(TransactionID id, String note) {
    this.id = id;
    this.note = note;
  }

  public TransactionID getId() {
    return id;
  }

  public void setId(TransactionID id) {
    this.id = id;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
