package com.cuenta.contador.service.auth;

import com.cuenta.contador.service.credential.Credential;
import com.cuenta.contador.service.credential.CredentialService;
import com.cuenta.contador.service.session.SessionService;
import com.cuenta.contador.service.user.User;
import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.service.user.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class AuthenticationServiceImpl implements AuthenticationService {
    private final CredentialService credentialService;
    private final SessionService sessionService;
    private final UserService userService;

    @Inject
    public AuthenticationServiceImpl(CredentialService credentialService, SessionService sessionService, UserService userService) {
        this.credentialService = credentialService;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @Override
    public UUID authenticate(String username, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalAccessException {
        Credential credential = credentialService.getCredentials(username);
        if (credential == null) {
            throw new IllegalArgumentException("Username not found");
        }
        if (!username.equals(credential.getUsername()) || !Encryptor.validatePassword(password, credential.getPassword())) {
            throw new IllegalArgumentException("Password does not match");
        }
        return createSession(username);
    }


    @Override
    public UUID createUser(Credential credential, User userInfo) throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalAccessException {
        UserID id = UserID.of(credentialService.storeCredentials(credential));
        userInfo.setId(id);
        userService.createUser(userInfo);
        return createSession(credential.getUsername());
    }

    @Override
    public UUID createSession(String username) throws IllegalAccessException {
        Credential credential = credentialService.getCredentials(username);
        if (credential == null) {
            throw new IllegalAccessException("User not found");
        }
        return sessionService.createSession(username);
    }

    @Override
    public void deleteSession(UUID  sessionId){
        sessionService.deleteSession(sessionId);
    }

    @Override
    public boolean isValidSession(UUID sessionId) {
        return sessionService.isValidSession(sessionId);
    }


}
