package com.cuenta.contador.store.account;

import com.cuenta.contador.infra.DSLContextProvider;
import com.cuenta.contador.infra.ID;
import com.cuenta.contador.jooq_auto_generated.tables.records.AccountRecord;
import com.cuenta.contador.service.account.Account;
import com.cuenta.contador.service.account.Account.AccountID;
import com.cuenta.contador.service.account.AccountNumber;
import com.cuenta.contador.service.user.User.UserID;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;

import jakarta.inject.Inject;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public boolean accountNumberExists(UserID userId, String accountNumber){
        AccountRecord record = db
          .selectFrom(ACCOUNT)
          .where(ACCOUNT.USER_ID.eq(userId.getIntId()))
          .and(ACCOUNT.NUMBER.eq(accountNumber))
          .fetchOne();
        return record != null;
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

    public List<AccountNumber> getAccountNumbers(UserID userId) {
        return db.select(ACCOUNT.ID, ACCOUNT.NAME, ACCOUNT.NUMBER)
          .from(ACCOUNT)
          .where(ACCOUNT.USER_ID.eq(userId.getIntId()))
          .fetch(record -> {
              String number = record.get(ACCOUNT.NUMBER);
              return new AccountNumber(
                record.get(ACCOUNT.ID),
                record.get(ACCOUNT.NAME) + " " + number.substring(number.length()-4)
              );
          });
    }

    public Map<String, BigDecimal> getBalanceByAccountType(UserID userId){
        return db
          .select(ACCOUNT.TYPE, DSL.sum(ACCOUNT.BALANCE))
          .from(ACCOUNT)
          .where(ACCOUNT.USER_ID.eq(userId.getIntId()))
          .groupBy(ACCOUNT.TYPE)
          .fetchMap(ACCOUNT.TYPE, DSL.sum(ACCOUNT.BALANCE));

    }


    public void updateAccount(UserID userId, AccountID accountId, Account account) {
         db.update(ACCOUNT)
                .set(ACCOUNT.NAME, account.getName())
                .set(ACCOUNT.NUMBER, account.getNumber())
                .set(ACCOUNT.TYPE, account.getType())
                .set(ACCOUNT.BALANCE, account.getBalance())
                .where(ACCOUNT.ID.eq(accountId.getIntId()))
                .and(ACCOUNT.USER_ID.eq(userId.getIntId()))
                .execute();
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

    public void updateAccountBalance(UserID userId, AccountID accountId, Double amount, String transactionType) {
        System.out.println("Updating account " + accountId + " with amount " + amount);
        if (transactionType.equals("Expense")){
            db.update(ACCOUNT).set(ACCOUNT.BALANCE, ACCOUNT.BALANCE.minus(amount)).where(ACCOUNT.USER_ID.eq(userId.getIntId())).and(ACCOUNT.ID.eq(accountId.getIntId())).execute();
        } else {
            db.update(ACCOUNT).set(ACCOUNT.BALANCE, ACCOUNT.BALANCE.plus(amount)).where(ACCOUNT.USER_ID.eq(userId.getIntId())).and(ACCOUNT.ID.eq(accountId.getIntId())).execute();
        }
    }
}
