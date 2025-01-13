package com.cuenta.contador.store.account;

import com.cuenta.contador.infra.DSLContextProvider;
import com.cuenta.contador.jooq_auto_generated.tables.records.AccountRecord;
import com.cuenta.contador.service.account.Account;
import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.user.User.UserID;
import org.jooq.DSLContext;

import javax.inject.Inject;

import static com.cuenta.contador.jooq_auto_generated.Tables.ACCOUNT;

public class AccountStore {
    private final DSLContext db;

    @Inject
    public AccountStore(DSLContextProvider dbProvider ){
        this.db = dbProvider.get();
    }

    public void storeAccount (Account account){
        AccountRecord record = toRecord(account);
        db.insertInto(ACCOUNT).set(record).execute();

    }

    public Account getAccount(AccountID id){
        AccountRecord record = db.selectFrom(ACCOUNT).where(ACCOUNT.ID.eq(id.getIntId())).fetchOne();
        if (record==null){
            return null;
        }
        return fromRecord(record);
    }

    private AccountRecord toRecord(Account account){
        AccountRecord record = new AccountRecord();
        record.setId(account.getAccountId().getIntId());
        record.setUserId(account.getUser_id().getIntId());
        record.setName(account.getName());
        record.setNumber(account.getNumber());
        record.setType(account.getType());
        record.getBalance();
        return record;
    }

    private Account fromRecord(AccountRecord record){
        Account account = new Account();
        account.setAccountId(AccountID.of(record.getId()));
        account.setUser_id(UserID.of(record.getUserId()));
        account.setName(record.getName());
        account.setNumber(record.getNumber());
        account.setType(record.getType());
        account.setBalance(record.getBalance());
        return account;
    }

}
