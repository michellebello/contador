package com.cuenta.contador.service.user;

import com.cuenta.contador.infra.Pair;
import com.cuenta.contador.service.user.User.UserID;

import java.util.UUID;

public class UserContext {
    private final static ThreadLocal<Pair<UUID, UserID>> userContext = new ThreadLocal<>();

    public static UserID getUserID() {
        if (userContext.get() == null) {
            throw new IllegalStateException("Tried to get user context without setting it first");
        }
        return userContext.get().getRight();
    }

    public static void setUserContext(UUID sessionId, UserID userId) {
        userContext.set(new Pair<>(sessionId, userId));
    }

    public static void clearUserContext() {
        if (userContext.get() != null) {
            userContext.remove();
        }
    }
}
