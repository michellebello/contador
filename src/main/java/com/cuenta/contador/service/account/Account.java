package com.cuenta.contador.service.account;
import com.cuenta.contador.infra.ID;
import com.cuenta.contador.service.user.User.UserID;

public class Account {
    private AccountID id;
    private UserID user_id;
    private String name;
    private String number;
    private String type;
    private double balance;


    public AccountID getAccountId(){
        return id;
    }

    public void setAccountId(AccountID id){
        this.id = id;
    }

    public UserID getUser_id() {
        return user_id;
    }

    public void setUser_id(UserID user_id) {
        this.user_id = user_id;
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                '}';
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
