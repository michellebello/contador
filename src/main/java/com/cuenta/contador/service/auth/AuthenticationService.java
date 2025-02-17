package com.cuenta.contador.service.auth;

import com.cuenta.contador.service.credential.Credential;
import com.cuenta.contador.service.user.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public interface AuthenticationService {
    UUID authenticate(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalAccessException;

    UUID createUser(Credential credential, User userInfo) throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalAccessException;

    UUID createSession(String username) throws IllegalAccessException;

    void deleteSession(UUID sessionId);

    boolean isValidSession(UUID sessionId);
}
