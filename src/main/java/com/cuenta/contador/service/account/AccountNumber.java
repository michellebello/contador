package com.cuenta.contador.service.account;

import com.cuenta.contador.service.account.Account.AccountID;

public class AccountNumber {
  private Integer accountId;
  private String formattedAccountNumber;

  public AccountNumber(Integer  accountId, String formattedAccountNumber){
    this.accountId = accountId;
    this.formattedAccountNumber = formattedAccountNumber;
  }

  public Integer  getAccountId(){
    return accountId;
  }

  public void setAccountId(Integer  accountId){
    this.accountId = accountId;
  }

  public String getFormattedAccountNumber(){
    return formattedAccountNumber;
  }

  public void setFormattedAccountNumber(String formattedAccountNumber){
    this.formattedAccountNumber = formattedAccountNumber;
  }
}
