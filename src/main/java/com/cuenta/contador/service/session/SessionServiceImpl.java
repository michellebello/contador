package com.cuenta.contador.service.session;

import com.cuenta.contador.store.session.SessionStore;

import jakarta.inject.Inject;
import java.util.UUID;

public class SessionServiceImpl implements SessionService {
    private final SessionStore sessionStore;

    @Inject
    public SessionServiceImpl(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public UUID createSession(String username) {
        return sessionStore.createSession(username);
    }

    @Override
    public boolean isValidSession(UUID sessionId) {
        return sessionStore.isValidSession(sessionId);
    }

    @Override
    public String getUsernameForSession(UUID sessionId) {
        return sessionStore.getUsernameForSession(sessionId);
    }
}
