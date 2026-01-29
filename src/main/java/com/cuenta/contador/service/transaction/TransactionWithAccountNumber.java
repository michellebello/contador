package com.cuenta.contador.service.transaction;

import com.cuenta.contador.service.account.Account;

import java.time.LocalDateTime;

public class TransactionWithAccountNumber extends Transaction{
  Transaction transaction;
  String accountNumber;


  public TransactionWithAccountNumber(TransactionID id, Account.AccountID accountId, String name, String type, Double amount, LocalDateTime createdOn, String accountNumber) {
    super(id, accountId, name, type, amount, createdOn);
    this.accountNumber = accountNumber;
  }

  public Transaction getTransaction() {
    return transaction;
  }

  public void setTransaction(Transaction transaction){
    this.transaction = transaction;
  }

  public String getAccountNumber(){
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber){
    this.accountNumber = accountNumber;
  }
}

