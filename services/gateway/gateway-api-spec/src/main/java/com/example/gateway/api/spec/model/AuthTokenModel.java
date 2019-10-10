package com.example.gateway.api.spec.model;

import java.io.Serializable;

public class AuthTokenModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    public AuthTokenModel() {
        //required by jackson
    }

    public AuthTokenModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
