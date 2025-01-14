package com.cuenta.contador.server.json.account;

import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.user.User.UserID;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountJson {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String number;
    @JsonProperty
    private String type;
    @JsonProperty
    private double balance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
