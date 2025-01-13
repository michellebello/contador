package com.cuenta.contador.service.context;

import com.cuenta.contador.service.credential.Credential;
import com.cuenta.contador.service.credential.CredentialService;
import com.cuenta.contador.service.session.SessionService;
import com.cuenta.contador.service.user.User;
import com.cuenta.contador.service.user.User.UserID;

import javax.inject.Inject;
import java.util.UUID;

public class ContextService {
    private final SessionService sessionService;
    private final CredentialService credentialService;

    @Inject
    public ContextService(SessionService sessionService, CredentialService credentialService) {
        this.sessionService = sessionService;
        this.credentialService = credentialService;
    }

    public UserID getUserID(UUID sessionId) {
        String username = sessionService.getUsernameForSession(sessionId);
        if (username == null) {
            throw new IllegalStateException("Session not found");
        }
        return credentialService.getUserID(username);
    }
}
