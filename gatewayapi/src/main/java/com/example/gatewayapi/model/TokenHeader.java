package com.example.gatewayapi.model;

public class TokenHeader {
    private String type;
    private String alg;

    public TokenHeader() {
        alg = "HS256";
    }
}
