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

import java.time.LocalDate;
import java.util.Arrays;
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
        Integer typeId = db.select(TRANSACTION_TYPE.ID).from(TRANSACTION_TYPE).where(TRANSACTION_TYPE.NAME.eq(transactionType)).execute();

        db.insertInto(TRANSACTION)
          .set(TRANSACTION.USER_ID, userId.getIntId())
          .set(TRANSACTION.ACCOUNT_ID, transaction.getAccountId().getIntId())
          .set(TRANSACTION.NAME, transaction.getName())
          .set(TRANSACTION.CATEGORY, transaction.getCategory())
          .set(TRANSACTION.TYPE_ID, typeId)
          .set(TRANSACTION.AMOUNT, transaction.getAmount())
          .set(TRANSACTION.CREATED_ON, transaction.getCreatedOn())
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
            TRANSACTION.CREATED_ON
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
          TRANSACTION.CREATED_ON
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

    public void updateTransaction(UserID userId, TransactionID transactionId, Transaction transaction) {
        db.update(TRANSACTION)
                .set(TRANSACTION.NAME, transaction.getName())
                .set(TRANSACTION.CATEGORY, transaction.getCategory())
                .set(TRANSACTION.AMOUNT, transaction.getAmount())
                .set(TRANSACTION.CREATED_ON, transaction.getCreatedOn())
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

    private Transaction fromRecord(TransactionRecord record){
        return new Transaction(
                TransactionID.of(record.getId()),
                AccountID.of(record.getAccountId()),
                record.getName(),
                record.getCategory(),
                record.getAmount(),
                record.getCreatedOn()
        );
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
          record.get(TRANSACTION.CREATED_ON)
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
