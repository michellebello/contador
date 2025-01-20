package com.cuenta.contador.store.account;

import com.cuenta.contador.infra.DSLContextProvider;
import com.cuenta.contador.infra.ID;
import com.cuenta.contador.jooq_auto_generated.tables.records.AccountRecord;
import com.cuenta.contador.service.account.Account;
import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.user.User.UserID;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;

import jakarta.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static com.cuenta.contador.jooq_auto_generated.Tables.ACCOUNT;

public class AccountStore {
    private final DSLContext db;

    @Inject
    public AccountStore(DSLContextProvider dbProvider ){
        this.db = dbProvider.get();
    }

    public void storeAccount(UserID userId, Account account){
        AccountRecord record = toRecord(userId, account);
        db.insertInto(ACCOUNT).set(record).execute();
    }

    public Account getAccount(UserID userId, AccountID id){
        AccountRecord record = db
                .selectFrom(ACCOUNT)
                .where(ACCOUNT.USER_ID.eq(userId.getIntId()))
                .and(ACCOUNT.ID.eq(id.getIntId()))
                .fetchOne();
        if (record == null){
            return null;
        }
        return fromRecord(record);
    }

    public List<Account> getAccounts(UserID userId, List<AccountID> ids) {
        SelectConditionStep<AccountRecord> partialQuery = db
                .selectFrom(ACCOUNT)
                .where(ACCOUNT.USER_ID.eq(userId.getIntId()));
        if (!ids.isEmpty()) {
            partialQuery = partialQuery.and(ACCOUNT.ID.in(ID.getIntIds(ids)));
        }
        return Arrays.stream(partialQuery.fetchArray()).map(this::fromRecord).toList();
    }

    public void deleteAccount(UserID userId,AccountID id) {
        db.deleteFrom(ACCOUNT)
                .where(ACCOUNT.USER_ID.eq(userId.getIntId()))
                .and(ACCOUNT.ID.eq(id.getIntId()))
                .execute();
    }

    private AccountRecord toRecord(UserID userId, Account account){
        AccountRecord record = new AccountRecord();
        record.setUserId(userId.getIntId());
        record.setName(account.getName());
        record.setNumber(account.getNumber());
        record.setType(account.getType());
        record.setBalance(account.getBalance());
        return record;
    }

    private Account fromRecord(AccountRecord record){
        return new Account(
                AccountID.of(record.getId()),
                record.getName(),
                record.getNumber(),
                record.getType(),
                record.getBalance());
    }
}
