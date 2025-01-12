package com.cuenta.contador.store.session;

import com.cuenta.contador.infra.DSLContextProvider;
import com.cuenta.contador.jooq_auto_generated.tables.records.SessionRecord;
import org.jooq.DSLContext;
import org.jooq.Record1;

import javax.inject.Inject;
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
