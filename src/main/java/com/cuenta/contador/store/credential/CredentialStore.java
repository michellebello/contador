package com.cuenta.contador.store.credential;

import com.cuenta.contador.infra.DSLContextProvider;
import com.cuenta.contador.jooq_auto_generated.tables.records.CredentialsRecord;
import com.cuenta.contador.service.credential.Credential;
import com.cuenta.contador.service.user.User;
import com.cuenta.contador.service.user.User.UserID;
import org.jooq.DSLContext;

import jakarta.inject.Inject;

import static com.cuenta.contador.jooq_auto_generated.Tables.CREDENTIALS;

public class CredentialStore {
    private final DSLContext db;

    @Inject
    public CredentialStore(DSLContextProvider dbProvider) {
        this.db = dbProvider.get();
    }

    public int storeCredentials(Credential credential) {
        db.insertInto(CREDENTIALS)
            .set(CREDENTIALS.USERNAME, credential.getUsername())
            .set(CREDENTIALS.PASSWORD, credential.getPassword())
            .execute();
        return db.select(CREDENTIALS.ID)
            .from(CREDENTIALS)
            .where(CREDENTIALS.USERNAME.eq(credential.getUsername()))
            .fetchOne()
            .get(CREDENTIALS.ID);
    }

    public void removeCredentials(String username) {
        db.deleteFrom(CREDENTIALS)
            .where(CREDENTIALS.USERNAME.eq(username))
            .execute();
    }

    public void updateCredentials(Credential credential) {
        db.update(CREDENTIALS)
            .set(CREDENTIALS.PASSWORD, credential.getPassword())
            .where(CREDENTIALS.USERNAME.eq(credential.getUsername()))
            .execute();
    }

    public Credential getCredentials(String username) {
        CredentialsRecord credentialsRecord = db.selectFrom(CREDENTIALS).where(CREDENTIALS.USERNAME.eq(username)).fetchOne();
        if (credentialsRecord == null) {
            return null;
        }
        return new Credential(credentialsRecord.getUsername(), credentialsRecord.getPassword());
    }

    public UserID getUserID(String username) {
        CredentialsRecord credentialsRecord = db.selectFrom(CREDENTIALS).where(CREDENTIALS.USERNAME.eq(username)).fetchOne();
        if (credentialsRecord == null) {
            return null;
        }
        return UserID.of(credentialsRecord.getId());
    }
}
