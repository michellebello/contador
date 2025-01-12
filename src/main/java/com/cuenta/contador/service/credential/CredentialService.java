package com.cuenta.contador.service.credential;

import com.cuenta.contador.service.user.User;
import com.cuenta.contador.service.user.User.UserID;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface CredentialService {
    int storeCredentials(Credential credential) throws NoSuchAlgorithmException, InvalidKeySpecException;

    void removeCredentials(String username);

    void updateCredentials(Credential credential);

    Credential getCredentials(String username);

    UserID getUserID(String username);
}
