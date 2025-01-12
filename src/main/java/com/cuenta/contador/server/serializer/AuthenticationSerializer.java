package com.cuenta.contador.server.serializer;

import com.cuenta.contador.infra.Pair;
import com.cuenta.contador.server.json.CredentialJson;
import com.cuenta.contador.server.json.RegisterJson;
import com.cuenta.contador.server.json.UserJson;
import com.cuenta.contador.service.credential.Credential;
import com.cuenta.contador.service.user.User;

public class AuthenticationSerializer {

    public Pair<Credential, User> fromRegisterJson(RegisterJson registerJson) {
        return new Pair<>(fromCredentialJson(registerJson.getCredentials()), fromUserJson(registerJson.getUserInfo()));
    }

    public Credential fromCredentialJson(CredentialJson credentialJson) {
        return new Credential(credentialJson.getUsername(), credentialJson.getPassword());
    }

    public User fromUserJson(UserJson userJson) {
        User user = new User();
        user.setFirstName(userJson.getFirstName());
        user.setLastName(userJson.getLastName());
        return user;
    }
}
