package com.example.account.exception;

public enum ExceptionCode {
    UNKNOWN("Unknown"),
    ACCOUNT_NOT_FOUND("Account not found"),
    ACCOUNT_EXISTS("Account already exists"),
    ACCOUNT_NAME_EXISTS("Account name already exists"),
    ACCOUNTS_NOT_FOUND("No accounts found for given request"),
    ROLE_NOT_FOUND("Role not found");

    private String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
