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
    private String type;
    private Double amount;
    private LocalDateTime createdOn;

    public Transaction(TransactionID id, AccountID accountId, String name, String type, Double amount, LocalDateTime createdOn){
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.createdOn = createdOn;
    }

    public Transaction(AccountID accountId, String name, String type, Double amount, LocalDateTime createdOn) {
        this.accountId = accountId;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.createdOn = createdOn;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(accountId, that.accountId) && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(amount, that.amount) && Objects.equals(createdOn, that.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, name, type, amount, createdOn);
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
