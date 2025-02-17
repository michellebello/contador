package com.cuenta.contador.service.session;

import java.util.UUID;

public interface SessionService {
    UUID createSession(String username);
    void deleteSession(UUID sessionId);

    boolean isValidSession(UUID sessionId);

    String getUsernameForSession(UUID sessionId);
}
