package com.example.gatewayapi.exception;

public enum ExceptionCode {
    UNKNOWN("Unknown"),
    TENANT_NOT_FOUND("Tenant not found"),
    ACCOUNT_NOT_FOUND("Account not found"),
    REQUEST_NOT_AUTHORIZED("Request not authorized")
    ;

    private String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
