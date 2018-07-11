package com.example.tenantapi.exception;

public enum ExceptionCode {
    UNKNOWN("Unknown"),
    TENANT_NOT_FOUND("Tenant not found");

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
