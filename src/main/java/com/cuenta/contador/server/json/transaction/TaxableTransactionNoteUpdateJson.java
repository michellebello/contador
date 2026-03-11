package com.cuenta.contador.server.json.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaxableTransactionNoteUpdateJson {
  @JsonProperty
  private Integer id;

  @JsonProperty
  private String note;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
