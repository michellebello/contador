package com.cuenta.contador.store.session;

import com.cuenta.contador.infra.DSLContextProvider;
import com.cuenta.contador.jooq_auto_generated.tables.Session;
import com.cuenta.contador.jooq_auto_generated.tables.records.SessionRecord;
import jakarta.ws.rs.core.Response;
import org.jooq.DSLContext;
import org.jooq.Record1;

import jakarta.inject.Inject;
import org.jooq.SQL;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static com.cuenta.contador.jooq_auto_generated.Tables.SESSION;

public class SessionStore {
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;
    private static final long SESSION_DURATION = 60 * 60 * 2; // 2 hours
    private final DSLContext db;
    @Inject
    SessionStore(DSLContextProvider dbProvider) {
        this.db = dbProvider.get();
    }

    public UUID createSession(String username) {
        UUID sessionId = UUID.randomUUID();
        SessionRecord sessionRecord = new SessionRecord();
        sessionRecord.setSession(sessionId.toString());
        sessionRecord.setUsername(username);
        sessionRecord.setExpiresOn(LocalDateTime.now().plusSeconds(SESSION_DURATION));
        db.insertInto(SESSION)
                .set(sessionRecord).execute();
        return sessionId;
    }

    public void deleteSession(UUID sessionId) {
        db.deleteFrom(SESSION).where(SESSION.SESSION_.eq(String.valueOf(sessionId))).execute();
    }

    public boolean isValidSession(UUID sessionId) {
        Record1<LocalDateTime> expirationDateTimeRecord = db.select(SESSION.EXPIRES_ON)
                .from(SESSION)
                .where(SESSION.SESSION_.eq(String.valueOf(sessionId)))
                .fetchOne();
        if (expirationDateTimeRecord == null) {
            return false;
        }

        LocalDateTime expirationDateTime = expirationDateTimeRecord.get(SESSION.EXPIRES_ON);
        return epochSecondsOn(expirationDateTime) > epochSecondsNow();
    }

    public String getUsernameForSession(UUID sessionId) {
        if (!isValidSession(sessionId)) {
            return null;
        }
        return db.select(SESSION.USERNAME)
                .from(SESSION)
                .where(SESSION.SESSION_.eq(String.valueOf(sessionId)))
                .fetchOne()
                .get(SESSION.USERNAME);
    }

    private long epochSecondsNow() {
        return epochSecondsOn(LocalDateTime.now());
    }

    private long epochSecondsOn(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZONE_OFFSET);
    }
}
