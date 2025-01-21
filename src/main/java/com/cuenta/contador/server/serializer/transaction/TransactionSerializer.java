package com.cuenta.contador.server.serializer.transaction;

import com.cuenta.contador.server.json.transaction.TransactionJson;
import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.transaction.Transaction;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TransactionSerializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // Register the JavaTimeModule to handle LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());
        // Disable writing dates as timestamps
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    // fromTransactionJson (takes in TransactionJson and returns a Transaction)
    public Transaction fromTransactionJson(TransactionJson transactionJson){
        return new Transaction(
                transactionJson.getId() == null ? null : TransactionID.of(transactionJson.getId()),
                AccountID.of(transactionJson.getAccountId()),
                transactionJson.getName(),
                transactionJson.getType(),
                transactionJson.getAmount(),
                transactionJson.getCreatedOn()
        );
    }

    // need to have partial fromTransactionJson for PATCH endpoint
    public Transaction fromPartialTransactionJson(TransactionJson transactionJson, Transaction currTransaction){
        return new Transaction(
                currTransaction.getId(),
                transactionJson.getAccountId() != null? AccountID.of(transactionJson.getAccountId()) : currTransaction.getAccountId(),
                transactionJson.getName() != null? transactionJson.getName() : currTransaction.getName(),
                transactionJson.getType() != null? transactionJson.getType() : currTransaction.getType(),
                transactionJson.getAmount() != null? transactionJson.getAmount() : currTransaction.getAmount(),
                transactionJson.getCreatedOn() != null? transactionJson.getCreatedOn() : currTransaction.getCreatedOn()
        );
    }

    // toTransactionJson (takes in Transaction and returns a TransactionJson)
    public TransactionJson toTransactionJson(Transaction transaction){
        TransactionJson transactionJson = new TransactionJson();
        transactionJson.setId(transaction.getId().getIntId());
        transactionJson.setAccountId(transaction.getAccountId().getIntId());
        transactionJson.setName(transaction.getName());
        transactionJson.setType(transaction.getType());
        transactionJson.setAmount(transaction.getAmount());
        transactionJson.setCreatedOn(transaction.getCreatedOn());
        return transactionJson;
    }
}
