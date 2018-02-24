package com.example.gatewayapi.model;

public class AuthToken {
    private TokenHeader header;
    private TokenPayload payload;
    private TokenSignature signature;
}
