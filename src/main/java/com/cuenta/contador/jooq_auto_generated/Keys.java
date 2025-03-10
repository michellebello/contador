/*
 * This file is generated by jOOQ.
 */
package com.cuenta.contador.jooq_auto_generated;


import com.cuenta.contador.jooq_auto_generated.tables.Account;
import com.cuenta.contador.jooq_auto_generated.tables.Credentials;
import com.cuenta.contador.jooq_auto_generated.tables.FlywaySchemaHistory;
import com.cuenta.contador.jooq_auto_generated.tables.Session;
import com.cuenta.contador.jooq_auto_generated.tables.Transaction;
import com.cuenta.contador.jooq_auto_generated.tables.User;
import com.cuenta.contador.jooq_auto_generated.tables.records.AccountRecord;
import com.cuenta.contador.jooq_auto_generated.tables.records.CredentialsRecord;
import com.cuenta.contador.jooq_auto_generated.tables.records.FlywaySchemaHistoryRecord;
import com.cuenta.contador.jooq_auto_generated.tables.records.SessionRecord;
import com.cuenta.contador.jooq_auto_generated.tables.records.TransactionRecord;
import com.cuenta.contador.jooq_auto_generated.tables.records.UserRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * contadordb.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AccountRecord> KEY_ACCOUNT_NUMBER = Internal.createUniqueKey(Account.ACCOUNT, DSL.name("KEY_account_number"), new TableField[] { Account.ACCOUNT.NUMBER }, true);
    public static final UniqueKey<AccountRecord> KEY_ACCOUNT_PRIMARY = Internal.createUniqueKey(Account.ACCOUNT, DSL.name("KEY_account_PRIMARY"), new TableField[] { Account.ACCOUNT.ID }, true);
    public static final UniqueKey<CredentialsRecord> KEY_CREDENTIALS_PRIMARY = Internal.createUniqueKey(Credentials.CREDENTIALS, DSL.name("KEY_credentials_PRIMARY"), new TableField[] { Credentials.CREDENTIALS.ID }, true);
    public static final UniqueKey<CredentialsRecord> KEY_CREDENTIALS_USERNAME = Internal.createUniqueKey(Credentials.CREDENTIALS, DSL.name("KEY_credentials_username"), new TableField[] { Credentials.CREDENTIALS.USERNAME }, true);
    public static final UniqueKey<FlywaySchemaHistoryRecord> KEY_FLYWAY_SCHEMA_HISTORY_PRIMARY = Internal.createUniqueKey(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, DSL.name("KEY_flyway_schema_history_PRIMARY"), new TableField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
    public static final UniqueKey<SessionRecord> KEY_SESSION_PRIMARY = Internal.createUniqueKey(Session.SESSION, DSL.name("KEY_session_PRIMARY"), new TableField[] { Session.SESSION.ID }, true);
    public static final UniqueKey<TransactionRecord> KEY_TRANSACTION_PRIMARY = Internal.createUniqueKey(Transaction.TRANSACTION, DSL.name("KEY_transaction_PRIMARY"), new TableField[] { Transaction.TRANSACTION.ID }, true);
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = Internal.createUniqueKey(User.USER, DSL.name("KEY_user_PRIMARY"), new TableField[] { User.USER.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AccountRecord, CredentialsRecord> ACCOUNT_IBFK_1 = Internal.createForeignKey(Account.ACCOUNT, DSL.name("account_ibfk_1"), new TableField[] { Account.ACCOUNT.USER_ID }, Keys.KEY_CREDENTIALS_PRIMARY, new TableField[] { Credentials.CREDENTIALS.ID }, true);
    public static final ForeignKey<SessionRecord, CredentialsRecord> SESSION_IBFK_1 = Internal.createForeignKey(Session.SESSION, DSL.name("session_ibfk_1"), new TableField[] { Session.SESSION.USERNAME }, Keys.KEY_CREDENTIALS_USERNAME, new TableField[] { Credentials.CREDENTIALS.USERNAME }, true);
    public static final ForeignKey<TransactionRecord, CredentialsRecord> TRANSACTION_IBFK_1 = Internal.createForeignKey(Transaction.TRANSACTION, DSL.name("transaction_ibfk_1"), new TableField[] { Transaction.TRANSACTION.USER_ID }, Keys.KEY_CREDENTIALS_PRIMARY, new TableField[] { Credentials.CREDENTIALS.ID }, true);
    public static final ForeignKey<TransactionRecord, AccountRecord> TRANSACTION_IBFK_2 = Internal.createForeignKey(Transaction.TRANSACTION, DSL.name("transaction_ibfk_2"), new TableField[] { Transaction.TRANSACTION.ACCOUNT_ID }, Keys.KEY_ACCOUNT_PRIMARY, new TableField[] { Account.ACCOUNT.ID }, true);
    public static final ForeignKey<UserRecord, CredentialsRecord> USER_IBFK_1 = Internal.createForeignKey(User.USER, DSL.name("user_ibfk_1"), new TableField[] { User.USER.ID }, Keys.KEY_CREDENTIALS_PRIMARY, new TableField[] { Credentials.CREDENTIALS.ID }, true);
}
