package com.cuenta.contador.store.transaction;

import com.cuenta.contador.infra.DSLContextProvider;
import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.transaction.Transaction;
import com.cuenta.contador.service.transaction.Transaction.TransactionID;
import com.cuenta.contador.jooq_auto_generated.tables.records.TransactionRecord;
import com.cuenta.contador.service.user.User.UserID;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectConditionStep;

import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.cuenta.contador.infra.ID.getIntIds;
import static com.cuenta.contador.jooq_auto_generated.Tables.TRANSACTION;

public class TransactionStore {
    private final DSLContext db;

    @Inject
    public TransactionStore(DSLContextProvider dbProvider){
        this.db = dbProvider.get();
    }

    public void storeTransaction(UserID userId, Transaction transaction){
        TransactionRecord record = toRecord(userId, transaction);
        db.insertInto(TRANSACTION).set(record).execute();
    }

    public Transaction getTransaction(UserID userId, TransactionID id) {
        TransactionRecord record = db
                .selectFrom(TRANSACTION)
                .where(TRANSACTION.USER_ID.eq(userId.getIntId()))
                .and(TRANSACTION.ID.eq(id.getIntId()))
                .fetchOne();
        if (record == null){
            return null;
        }
        return fromRecord(record);
    }

    public List<Transaction> getTransactions(UserID userId, List<TransactionID> ids, LocalDate after, LocalDate before){
        SelectConditionStep<TransactionRecord> partialQuery = db
                .selectFrom(TRANSACTION)
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
        return Arrays.stream(partialQuery.fetchArray()).map(this::fromRecord).toList();
    }

    public void updateTransaction(UserID userId, TransactionID transactionId, Transaction transaction) {
        db.update(TRANSACTION)
                .set(TRANSACTION.NAME, transaction.getName())
                .set(TRANSACTION.TYPE, transaction.getType())
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
                record.getType(),
                record.getAmount(),
                record.getCreatedOn()
        );
    }

    private TransactionRecord toRecord(UserID userId, Transaction transaction) {
        TransactionRecord record = new TransactionRecord();
        record.setUserId(userId.getIntId());
        record.setAccountId(transaction.getAccountId().getIntId());
        record.setName(transaction.getName());
        record.setType(transaction.getType());
        record.setAmount(transaction.getAmount());
        record.setCreatedOn(transaction.getCreatedOn());
        return record;
    }
}
