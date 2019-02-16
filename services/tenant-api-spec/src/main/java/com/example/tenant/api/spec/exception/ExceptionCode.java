package com.example.tenant.api.spec.exception;

public enum ExceptionCode {
    UNKNOWN("Unknown"),
    BAD_REQUEST("Bad request"),
    TENANT_NOT_FOUND("Tenant not found");

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
