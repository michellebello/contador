package com.cuenta.contador.service.transaction;

import com.cuenta.contador.infra.ID;
import com.cuenta.contador.service.account.Account.AccountID;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private TransactionID id;
    private AccountID accountId;
    private String accountNumber;
    private String name;
    private String category;
    private String typeName;
    private Double amount;
    private LocalDateTime createdOn;
    private Boolean isTaxable;

    public Transaction(TransactionID id, AccountID accountId, String accountNumber, String name, String category, String typeName, Double amount, LocalDateTime createdOn){
        this.id = id;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.name = name;
        this.category = category;
        this.typeName = typeName;
        this.amount = amount;
        this.createdOn = createdOn;
    }

    public Transaction(TransactionID id, AccountID accountId, String accountNumber, String name, String category, String typeName, Double amount, LocalDateTime createdOn, Boolean isTaxable){
        this.id = id;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.name = name;
        this.category = category;
        this.typeName = typeName;
        this.amount = amount;
        this.createdOn = createdOn;
        this.isTaxable = isTaxable;
    }

    public Transaction(TransactionID id, AccountID accountId, String name, String category, String typeName, Double amount, LocalDateTime createdOn, Boolean isTaxable) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.category = category;
        this.typeName = typeName;
        this.amount = amount;
        this.createdOn = createdOn;
        this.isTaxable = isTaxable;
    }

    public Transaction(TransactionID id, AccountID accountId, String name, String category, Double amount, LocalDateTime createdOn, Boolean isTaxable) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.createdOn = createdOn;
        this.isTaxable = isTaxable;
    }


    public TransactionID getId() {
        return id;
    }

    public void setId(TransactionID id) {
        this.id = id;
    }

    public AccountID getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountID accountId) {
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

    public Boolean getIsTaxable(){
        return isTaxable;
    }

    public void setIsTaxable(Boolean isTaxable){
        this.isTaxable = isTaxable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(accountId, that.accountId) && Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(amount, that.amount) && Objects.equals(createdOn, that.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, name, category, amount, createdOn);
    }

    public static class TransactionID extends ID<Transaction> {
        public TransactionID(int id){
            super(id);
        }

        public static TransactionID of(int id){
            return new TransactionID(id);
        }
    }

}
