package com.cuenta.contador.service.credential;

import com.cuenta.contador.service.auth.Encryptor;
import com.cuenta.contador.service.user.User;
import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.store.credential.CredentialStore;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class CredentialServiceImpl implements CredentialService {
    private final CredentialStore credentialStore;

    @Inject
    public CredentialServiceImpl(CredentialStore credentialStore) {
        this.credentialStore = credentialStore;
    }

    @Override
    public int storeCredentials(Credential credential) throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalArgumentException {
        if (getCredentials(credential.getUsername()) != null) {
            throw new IllegalArgumentException("User already exists");
        }
        Credential hashedCredential = new Credential(credential.getUsername(), Encryptor.createHash(credential.getPassword()));
        return credentialStore.storeCredentials(hashedCredential);
    }

    @Override
    public void removeCredentials(String username) {
        credentialStore.removeCredentials(username);
    }

    @Override
    public void updateCredentials(Credential credential) {
        credentialStore.updateCredentials(credential);
    }

    @Override
    public Credential getCredentials(String username) {
        return credentialStore.getCredentials(username);
    }

    @Override
    public UserID getUserID(String username) {
        return credentialStore.getUserID(username);
    }
}
