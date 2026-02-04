package com.cuenta.contador.service.account;
import com.cuenta.contador.infra.ID;


import java.util.Objects;

public class Account {
    private AccountID id;
    private String name;
    private String number;
    private String type;
    private Double balance;

    public Account(AccountID id, String name, String number, String type, double balance){
        this.id = id;
        this.name = name;
        this.number = number;
        this.type = type;
        this.balance = balance;
    }

    public Account(String name, String number, String type, double balance){
        this.name = name;
        this.number = number;
        this.type = type;
        this.balance = balance;
    }

    public AccountID getId() {
        return id;
    }

    public void setId(AccountID id) {
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(balance, account.balance) == 0 && Objects.equals(id, account.id) && Objects.equals(name, account.name) && Objects.equals(number, account.number) && Objects.equals(type, account.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, number, type, balance);
    }

    public static class AccountID extends ID<Account> {
        public AccountID(int id) {
            super(id);
        }

        public static AccountID of(int id) {
            return new AccountID(id);
        }
    }
}
