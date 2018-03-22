package com.example.web.gateway;

public class AuthToken {
    private String token;

    public AuthToken() {
        //required by jackson
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
