package com.cuenta.contador.server.json.credential;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CredentialJson {
    @JsonProperty
    private String username;

    @JsonProperty
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
