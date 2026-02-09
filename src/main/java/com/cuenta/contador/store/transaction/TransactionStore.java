package com.cuenta.contador.store.transaction;

import com.cuenta.contador.infra.DSLContextProvider;
import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.transaction.Transaction;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;
import com.cuenta.contador.jooq_auto_generated.tables.records.TransactionRecord;
import com.cuenta.contador.service.user.User.UserID;
import org.jooq.*;

import jakarta.inject.Inject;
import org.jooq.Record;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import static com.cuenta.contador.infra.ID.getIntIds;
import static com.cuenta.contador.jooq_auto_generated.Tables.ACCOUNT;
import static com.cuenta.contador.jooq_auto_generated.Tables.TRANSACTION;
import static com.cuenta.contador.jooq_auto_generated.Tables.TRANSACTION_TYPE;

public class TransactionStore {
    private final DSLContext db;

    @Inject
    public TransactionStore(DSLContextProvider dbProvider){
        this.db = dbProvider.get();
    }


    public void storeTransaction(UserID userId, Transaction transaction){
        String transactionType = transaction.getTypeName();
        System.out.println("transaction.isTaxable sent equals " + transaction.getIsTaxable());
        Integer typeId = db.select(TRANSACTION_TYPE.ID).from(TRANSACTION_TYPE).where(TRANSACTION_TYPE.NAME.eq(transactionType)).execute();
        db.insertInto(TRANSACTION)
          .set(TRANSACTION.USER_ID, userId.getIntId())
          .set(TRANSACTION.ACCOUNT_ID, transaction.getAccountId().getIntId())
          .set(TRANSACTION.NAME, transaction.getName())
          .set(TRANSACTION.CATEGORY, transaction.getCategory())
          .set(TRANSACTION.TYPE_ID, typeId)
          .set(TRANSACTION.AMOUNT, transaction.getAmount())
          .set(TRANSACTION.CREATED_ON, transaction.getCreatedOn())
          .set(TRANSACTION.IS_TAXABLE, transaction.getIsTaxable())
          .execute();
    }

    public Transaction getTransaction(UserID userId, TransactionID id) {
        var record = db.select(
            TRANSACTION.ID,
            TRANSACTION.USER_ID,
            TRANSACTION.ACCOUNT_ID,
            ACCOUNT.NAME,
            ACCOUNT.NUMBER,
            TRANSACTION.NAME,
            TRANSACTION.CATEGORY,
            TRANSACTION_TYPE.NAME,
            TRANSACTION.AMOUNT,
            TRANSACTION.CREATED_ON,
            TRANSACTION.IS_TAXABLE
          )
          .from(TRANSACTION)
          .join(ACCOUNT).on(TRANSACTION.ACCOUNT_ID.eq(ACCOUNT.ID))
          .join(TRANSACTION_TYPE).on(TRANSACTION.TYPE_ID.eq(TRANSACTION_TYPE.ID))
          .where(
            TRANSACTION.USER_ID.eq(userId.getIntId())
              .and(TRANSACTION.ID.eq(id.getIntId()))
          )
          .fetchOne();
        if (record == null) return null;

        return fromJoinedRecord(record);
    }


    public List<Transaction> getTransactions(UserID userId, List<TransactionID> ids, LocalDate after, LocalDate before){
        var partialQuery = db.select(
          TRANSACTION.ID,
          TRANSACTION.ACCOUNT_ID,
          ACCOUNT.NAME,
          ACCOUNT.NUMBER,
          TRANSACTION.NAME,
          TRANSACTION.CATEGORY,
          TRANSACTION_TYPE.NAME,
          TRANSACTION.AMOUNT,
          TRANSACTION.CREATED_ON,
          TRANSACTION.IS_TAXABLE
        )
          .from(TRANSACTION)
          .join(ACCOUNT).on(TRANSACTION.ACCOUNT_ID.eq(ACCOUNT.ID))
          .join(TRANSACTION_TYPE).on(TRANSACTION.TYPE_ID.eq(TRANSACTION_TYPE.ID))
          .where(TRANSACTION.USER_ID.eq(userId.getIntId()));

        if (!ids.isEmpty()){
            partialQuery = partialQuery.and(TRANSACTION.ID.in(getIntIds(ids)));
        }
        if (after != null) {
            partialQuery = partialQuery.and(TRANSACTION.CREATED_ON.ge(after.atStartOfDay()));
        }

        if (before != null) {
            partialQuery = partialQuery.and(TRANSACTION.CREATED_ON.lt(before.plusDays(1).atStartOfDay()));
        }

        return partialQuery
          .orderBy(TRANSACTION.CREATED_ON.desc())
          .fetch()
          .map(this::fromJoinedRecord);
    }

    public String getTransactionType(UserID userId, TransactionID transactionId) {
        return db.select(TRANSACTION_TYPE.NAME)
          .from(TRANSACTION_TYPE)
          .join(TRANSACTION)
          .on(TRANSACTION.TYPE_ID.eq(TRANSACTION_TYPE.ID))
          .where(TRANSACTION.ID.eq(transactionId.getIntId()))
          .and(TRANSACTION.USER_ID.eq(userId.getIntId()))
          .fetchOne(
            r-> r.get(TRANSACTION_TYPE.NAME)
          );
    }

    public double getTransactionAmount(UserID userId, TransactionID transactionId){
        Double amount =  db.select(TRANSACTION.AMOUNT)
          .from(TRANSACTION)
          .where(TRANSACTION.ID.eq(transactionId.getIntId()))
          .and(TRANSACTION.USER_ID.eq(userId.getIntId()))
          .fetchOne(TRANSACTION.AMOUNT);
        return amount != null ? amount : 0.0;
    }

    public void updateTransaction(UserID userId, TransactionID transactionId, Transaction transaction) {
        var partialQuery = db.update(TRANSACTION)
                .set(TRANSACTION.NAME, transaction.getName())
                .set(TRANSACTION.CATEGORY, transaction.getCategory())
                .set(TRANSACTION.AMOUNT, transaction.getAmount())
                .set(TRANSACTION.CREATED_ON, transaction.getCreatedOn())
                .set(TRANSACTION.IS_TAXABLE, transaction.getIsTaxable())
                .where(TRANSACTION.ID.eq(transactionId.getIntId()))
                .and(TRANSACTION.USER_ID.eq(userId.getIntId()))
                .execute();
    }

    public void deleteTransaction(UserID userId, TransactionID transactionId){
        db.deleteFrom(TRANSACTION)
                .where(TRANSACTION.USER_ID.eq(userId.getIntId()))
                .and(TRANSACTION.ID.eq(transactionId.getIntId()))
                .execute();
    }


    private Transaction fromJoinedRecord(Record record) {
        String last4AccountNum = record.get(ACCOUNT.NUMBER).substring(record.get(ACCOUNT.NUMBER).length()-4);
        String accountName = record.get(ACCOUNT.NAME);
        String formattedAccountNum = accountName + " " + last4AccountNum;

        return new Transaction(
          TransactionID.of(record.get(TRANSACTION.ID)),
          AccountID.of(record.get(TRANSACTION.ACCOUNT_ID)),
          formattedAccountNum,
          record.get(TRANSACTION.NAME),
          record.get(TRANSACTION.CATEGORY),
          record.get(TRANSACTION_TYPE.NAME),
          record.get(TRANSACTION.AMOUNT),
          record.get(TRANSACTION.CREATED_ON),
          record.get(TRANSACTION.IS_TAXABLE)
        );

    }

    private TransactionRecord toRecord(UserID userId, Transaction transaction) {
        TransactionRecord record = new TransactionRecord();
        record.setUserId(userId.getIntId());
        record.setAccountId(transaction.getAccountId().getIntId());
        record.setName(transaction.getName());
        record.setCategory(transaction.getCategory());
        record.setAmount(transaction.getAmount());
        record.setCreatedOn(transaction.getCreatedOn());
        return record;
    }
}
