/*
 * This file is generated by jOOQ.
 */
package com.cuenta.contador.jooq_auto_generated.tables;


import com.cuenta.contador.jooq_auto_generated.Contadordb;
import com.cuenta.contador.jooq_auto_generated.Indexes;
import com.cuenta.contador.jooq_auto_generated.Keys;
import com.cuenta.contador.jooq_auto_generated.tables.Credentials.CredentialsPath;
import com.cuenta.contador.jooq_auto_generated.tables.records.SessionRecord;

import java.time.LocalDateTime;
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
public class Session extends TableImpl<SessionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>contadordb.session</code>
     */
    public static final Session SESSION = new Session();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SessionRecord> getRecordType() {
        return SessionRecord.class;
    }

    /**
     * The column <code>contadordb.session.id</code>.
     */
    public final TableField<SessionRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>contadordb.session.session</code>.
     */
    public final TableField<SessionRecord, String> SESSION_ = createField(DSL.name("session"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>contadordb.session.username</code>.
     */
    public final TableField<SessionRecord, String> USERNAME = createField(DSL.name("username"), SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * The column <code>contadordb.session.expires_on</code>.
     */
    public final TableField<SessionRecord, LocalDateTime> EXPIRES_ON = createField(DSL.name("expires_on"), SQLDataType.LOCALDATETIME(0).nullable(false), this, "");

    private Session(Name alias, Table<SessionRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Session(Name alias, Table<SessionRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>contadordb.session</code> table reference
     */
    public Session(String alias) {
        this(DSL.name(alias), SESSION);
    }

    /**
     * Create an aliased <code>contadordb.session</code> table reference
     */
    public Session(Name alias) {
        this(alias, SESSION);
    }

    /**
     * Create a <code>contadordb.session</code> table reference
     */
    public Session() {
        this(DSL.name("session"), null);
    }

    public <O extends Record> Session(Table<O> path, ForeignKey<O, SessionRecord> childPath, InverseForeignKey<O, SessionRecord> parentPath) {
        super(path, childPath, parentPath, SESSION);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class SessionPath extends Session implements Path<SessionRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> SessionPath(Table<O> path, ForeignKey<O, SessionRecord> childPath, InverseForeignKey<O, SessionRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private SessionPath(Name alias, Table<SessionRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public SessionPath as(String alias) {
            return new SessionPath(DSL.name(alias), this);
        }

        @Override
        public SessionPath as(Name alias) {
            return new SessionPath(alias, this);
        }

        @Override
        public SessionPath as(Table<?> alias) {
            return new SessionPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Contadordb.CONTADORDB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.SESSION_USERNAME);
    }

    @Override
    public Identity<SessionRecord, Integer> getIdentity() {
        return (Identity<SessionRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<SessionRecord> getPrimaryKey() {
        return Keys.KEY_SESSION_PRIMARY;
    }

    @Override
    public List<ForeignKey<SessionRecord, ?>> getReferences() {
        return Arrays.asList(Keys.SESSION_IBFK_1);
    }

    private transient CredentialsPath _credentials;

    /**
     * Get the implicit join path to the <code>contadordb.credentials</code>
     * table.
     */
    public CredentialsPath credentials() {
        if (_credentials == null)
            _credentials = new CredentialsPath(this, Keys.SESSION_IBFK_1, null);

        return _credentials;
    }

    @Override
    public Session as(String alias) {
        return new Session(DSL.name(alias), this);
    }

    @Override
    public Session as(Name alias) {
        return new Session(alias, this);
    }

    @Override
    public Session as(Table<?> alias) {
        return new Session(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Session rename(String name) {
        return new Session(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Session rename(Name name) {
        return new Session(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Session rename(Table<?> name) {
        return new Session(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Session where(Condition condition) {
        return new Session(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Session where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Session where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Session where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Session where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Session where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Session where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Session where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Session whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Session whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
