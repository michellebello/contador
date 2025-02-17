package com.cuenta.contador.server.json.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserJson {
        @JsonProperty
        String firstName;

        @JsonProperty
        String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
