package com.cuenta.contador.server.json.user;

import com.cuenta.contador.server.json.account.AccountJson;
import com.cuenta.contador.server.json.credential.CredentialJson;
import com.cuenta.contador.server.json.user.UserJson;
import com.cuenta.contador.service.account.Account;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterJson {
    @JsonProperty
    private CredentialJson credentials;

    @JsonProperty
    private UserJson userInfo;

    public CredentialJson getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialJson credentials) {
        this.credentials = credentials;
    }

    public UserJson getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserJson userInfo) {
        this.userInfo = userInfo;
    }
}
