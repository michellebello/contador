/*
 * This file is generated by jOOQ.
 */
package com.cuenta.contador.jooq_auto_generated.tables;


import com.cuenta.contador.jooq_auto_generated.Contadordb;
import com.cuenta.contador.jooq_auto_generated.Indexes;
import com.cuenta.contador.jooq_auto_generated.Keys;
import com.cuenta.contador.jooq_auto_generated.tables.records.FlywaySchemaHistoryRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
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
public class FlywaySchemaHistory extends TableImpl<FlywaySchemaHistoryRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>contadordb.flyway_schema_history</code>
     */
    public static final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = new FlywaySchemaHistory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FlywaySchemaHistoryRecord> getRecordType() {
        return FlywaySchemaHistoryRecord.class;
    }

    /**
     * The column <code>contadordb.flyway_schema_history.installed_rank</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, Integer> INSTALLED_RANK = createField(DSL.name("installed_rank"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>contadordb.flyway_schema_history.version</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, String> VERSION = createField(DSL.name("version"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>contadordb.flyway_schema_history.description</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.VARCHAR(200).nullable(false), this, "");

    /**
     * The column <code>contadordb.flyway_schema_history.type</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, String> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * The column <code>contadordb.flyway_schema_history.script</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, String> SCRIPT = createField(DSL.name("script"), SQLDataType.VARCHAR(1000).nullable(false), this, "");

    /**
     * The column <code>contadordb.flyway_schema_history.checksum</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, Integer> CHECKSUM = createField(DSL.name("checksum"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>contadordb.flyway_schema_history.installed_by</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, String> INSTALLED_BY = createField(DSL.name("installed_by"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>contadordb.flyway_schema_history.installed_on</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, LocalDateTime> INSTALLED_ON = createField(DSL.name("installed_on"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>contadordb.flyway_schema_history.execution_time</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, Integer> EXECUTION_TIME = createField(DSL.name("execution_time"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>contadordb.flyway_schema_history.success</code>.
     */
    public final TableField<FlywaySchemaHistoryRecord, Byte> SUCCESS = createField(DSL.name("success"), SQLDataType.TINYINT.nullable(false), this, "");

    private FlywaySchemaHistory(Name alias, Table<FlywaySchemaHistoryRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private FlywaySchemaHistory(Name alias, Table<FlywaySchemaHistoryRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>contadordb.flyway_schema_history</code> table
     * reference
     */
    public FlywaySchemaHistory(String alias) {
        this(DSL.name(alias), FLYWAY_SCHEMA_HISTORY);
    }

    /**
     * Create an aliased <code>contadordb.flyway_schema_history</code> table
     * reference
     */
    public FlywaySchemaHistory(Name alias) {
        this(alias, FLYWAY_SCHEMA_HISTORY);
    }

    /**
     * Create a <code>contadordb.flyway_schema_history</code> table reference
     */
    public FlywaySchemaHistory() {
        this(DSL.name("flyway_schema_history"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Contadordb.CONTADORDB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.FLYWAY_SCHEMA_HISTORY_FLYWAY_SCHEMA_HISTORY_S_IDX);
    }

    @Override
    public UniqueKey<FlywaySchemaHistoryRecord> getPrimaryKey() {
        return Keys.KEY_FLYWAY_SCHEMA_HISTORY_PRIMARY;
    }

    @Override
    public FlywaySchemaHistory as(String alias) {
        return new FlywaySchemaHistory(DSL.name(alias), this);
    }

    @Override
    public FlywaySchemaHistory as(Name alias) {
        return new FlywaySchemaHistory(alias, this);
    }

    @Override
    public FlywaySchemaHistory as(Table<?> alias) {
        return new FlywaySchemaHistory(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public FlywaySchemaHistory rename(String name) {
        return new FlywaySchemaHistory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FlywaySchemaHistory rename(Name name) {
        return new FlywaySchemaHistory(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public FlywaySchemaHistory rename(Table<?> name) {
        return new FlywaySchemaHistory(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public FlywaySchemaHistory where(Condition condition) {
        return new FlywaySchemaHistory(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public FlywaySchemaHistory where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public FlywaySchemaHistory where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public FlywaySchemaHistory where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public FlywaySchemaHistory where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public FlywaySchemaHistory where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public FlywaySchemaHistory where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public FlywaySchemaHistory where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public FlywaySchemaHistory whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public FlywaySchemaHistory whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
