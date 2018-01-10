package com.example.account.exception;

public enum ExceptionCode {
    UNKNOWN("Unknown"),
    ACCOUNT_NOT_FOUND("Account not found"),
    ROLE_NOT_FOUND("Role not found");

    private String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
