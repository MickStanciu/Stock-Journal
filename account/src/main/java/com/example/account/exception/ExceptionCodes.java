package com.example.account.exception;

public enum ExceptionCodes {
    ACCOUNT_NOT_FOUND("Account not found");

    private String message;

    ExceptionCodes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
