package com.cuenta.contador.server.json.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class TransactionJson {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private Integer accountId;
    @JsonProperty
    private String accountNumber;
    @JsonProperty
    private String name;
    @JsonProperty
    private String typeName;
    @JsonProperty
    private String category;
    @JsonProperty
    private Double amount;
    @JsonProperty
//    @DateTimeFormat(iso = DateTimeFormatter.ISO_LOCAL_DATE_TIME)
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
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

    public String getAccountNumber(){
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTypeName(){
        return typeName;
    }

    public void setType(String typeName){
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
