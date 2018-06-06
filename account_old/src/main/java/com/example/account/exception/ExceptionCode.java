package com.example.account.exception;

public enum ExceptionCode {
    UNKNOWN("Unknown"),
    ACCOUNT_NOT_FOUND("AccountModel not found"),
    ACCOUNT_EXISTS("AccountModel already exists"),
    ACCOUNT_NAME_EXISTS("AccountModel name already exists"),
    ACCOUNTS_NOT_FOUND("No accounts found for given request"),
    ROLE_NOT_FOUND("RoleModel not found");

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
