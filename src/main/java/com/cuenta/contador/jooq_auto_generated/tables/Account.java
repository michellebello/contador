/*
 * This file is generated by jOOQ.
 */
package com.cuenta.contador.jooq_auto_generated.tables;


import com.cuenta.contador.jooq_auto_generated.Contadordb;
import com.cuenta.contador.jooq_auto_generated.Indexes;
import com.cuenta.contador.jooq_auto_generated.Keys;
import com.cuenta.contador.jooq_auto_generated.tables.Credentials.CredentialsPath;
import com.cuenta.contador.jooq_auto_generated.tables.Transaction.TransactionPath;
import com.cuenta.contador.jooq_auto_generated.tables.records.AccountRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Account extends TableImpl<AccountRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>contadordb.account</code>
     */
    public static final Account ACCOUNT = new Account();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AccountRecord> getRecordType() {
        return AccountRecord.class;
    }

    /**
     * The column <code>contadordb.account.id</code>.
     */
    public final TableField<AccountRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>contadordb.account.user_id</code>.
     */
    public final TableField<AccountRecord, Integer> USER_ID = createField(DSL.name("user_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>contadordb.account.name</code>.
     */
    public final TableField<AccountRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>contadordb.account.number</code>.
     */
    public final TableField<AccountRecord, String> NUMBER = createField(DSL.name("number"), SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>contadordb.account.type</code>.
     */
    public final TableField<AccountRecord, String> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>contadordb.account.balance</code>.
     */
    public final TableField<AccountRecord, Double> BALANCE = createField(DSL.name("balance"), SQLDataType.DOUBLE.nullable(false), this, "");

    private Account(Name alias, Table<AccountRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Account(Name alias, Table<AccountRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>contadordb.account</code> table reference
     */
    public Account(String alias) {
        this(DSL.name(alias), ACCOUNT);
    }

    /**
     * Create an aliased <code>contadordb.account</code> table reference
     */
    public Account(Name alias) {
        this(alias, ACCOUNT);
    }

    /**
     * Create a <code>contadordb.account</code> table reference
     */
    public Account() {
        this(DSL.name("account"), null);
    }

    public <O extends Record> Account(Table<O> path, ForeignKey<O, AccountRecord> childPath, InverseForeignKey<O, AccountRecord> parentPath) {
        super(path, childPath, parentPath, ACCOUNT);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class AccountPath extends Account implements Path<AccountRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> AccountPath(Table<O> path, ForeignKey<O, AccountRecord> childPath, InverseForeignKey<O, AccountRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private AccountPath(Name alias, Table<AccountRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public AccountPath as(String alias) {
            return new AccountPath(DSL.name(alias), this);
        }

        @Override
        public AccountPath as(Name alias) {
            return new AccountPath(alias, this);
        }

        @Override
        public AccountPath as(Table<?> alias) {
            return new AccountPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Contadordb.CONTADORDB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.ACCOUNT_USER_ID);
    }

    @Override
    public Identity<AccountRecord, Integer> getIdentity() {
        return (Identity<AccountRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<AccountRecord> getPrimaryKey() {
        return Keys.KEY_ACCOUNT_PRIMARY;
    }

    @Override
    public List<UniqueKey<AccountRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_ACCOUNT_NUMBER);
    }

    @Override
    public List<ForeignKey<AccountRecord, ?>> getReferences() {
        return Arrays.asList(Keys.ACCOUNT_IBFK_1);
    }

    private transient CredentialsPath _credentials;

    /**
     * Get the implicit join path to the <code>contadordb.credentials</code>
     * table.
     */
    public CredentialsPath credentials() {
        if (_credentials == null)
            _credentials = new CredentialsPath(this, Keys.ACCOUNT_IBFK_1, null);

        return _credentials;
    }

    private transient TransactionPath _transaction;

    /**
     * Get the implicit to-many join path to the
     * <code>contadordb.transaction</code> table
     */
    public TransactionPath transaction() {
        if (_transaction == null)
            _transaction = new TransactionPath(this, null, Keys.TRANSACTION_IBFK_2.getInverseKey());

        return _transaction;
    }

    @Override
    public Account as(String alias) {
        return new Account(DSL.name(alias), this);
    }

    @Override
    public Account as(Name alias) {
        return new Account(alias, this);
    }

    @Override
    public Account as(Table<?> alias) {
        return new Account(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Account rename(String name) {
        return new Account(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Account rename(Name name) {
        return new Account(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Account rename(Table<?> name) {
        return new Account(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Account where(Condition condition) {
        return new Account(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Account where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Account where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Account where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Account where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Account where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Account where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Account where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Account whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Account whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
