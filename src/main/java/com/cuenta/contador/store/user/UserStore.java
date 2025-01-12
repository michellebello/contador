package com.cuenta.contador.store.user;

import com.cuenta.contador.infra.DSLContextProvider;
import com.cuenta.contador.jooq_auto_generated.tables.records.UserRecord;
import com.cuenta.contador.service.user.User;
import com.cuenta.contador.service.user.User.UserID;
import org.jooq.DSLContext;

import javax.inject.Inject;

import static com.cuenta.contador.jooq_auto_generated.Tables.USER;

public class UserStore {
    private final DSLContext db;

    @Inject
    public UserStore(DSLContextProvider dbProvider) {
        this.db = dbProvider.get();
    }

    public void storeUser(User user) {
        UserRecord record = toRecord(user);
        db.insertInto(USER)
                .set(record)
                .execute();
    }

    public User getUser(UserID id) {
        UserRecord record = db.selectFrom(USER)
                .where(USER.ID.eq(id.getIntId()))
                .fetchOne();
        if (record == null) {
            return null;
        }

        return fromRecord(record);
    }

    private User fromRecord(UserRecord record) {
        User user = new User();
        user.setId(UserID.of(record.getId()));
        user.setFirstName(record.getFirstName());
        user.setLastName(record.getLastName());
        return user;
    }

    private UserRecord toRecord(User user) {
        UserRecord record = new UserRecord();
        record.setId(user.getId().getIntId());
        record.setFirstName(user.getFirstName());
        record.setLastName(user.getLastName());
        return record;
    }
}
