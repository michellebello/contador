package com.cuenta.contador.server.json;

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
