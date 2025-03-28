/*
 * This file is generated by jOOQ.
 */
package com.cuenta.contador.jooq_auto_generated.tables.records;


import com.cuenta.contador.jooq_auto_generated.tables.Credentials;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class CredentialsRecord extends UpdatableRecordImpl<CredentialsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>contadordb.credentials.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>contadordb.credentials.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>contadordb.credentials.username</code>.
     */
    public void setUsername(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>contadordb.credentials.username</code>.
     */
    public String getUsername() {
        return (String) get(1);
    }

    /**
     * Setter for <code>contadordb.credentials.password</code>.
     */
    public void setPassword(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>contadordb.credentials.password</code>.
     */
    public String getPassword() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CredentialsRecord
     */
    public CredentialsRecord() {
        super(Credentials.CREDENTIALS);
    }

    /**
     * Create a detached, initialised CredentialsRecord
     */
    public CredentialsRecord(Integer id, String username, String password) {
        super(Credentials.CREDENTIALS);

        setId(id);
        setUsername(username);
        setPassword(password);
        resetChangedOnNotNull();
    }
}
