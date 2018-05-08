package com.example.web.exception;

public enum ExceptionCode {
    UNKNOWN("Unknown"),
    MISSING_COOKIE("Missing Cookie"),
    MISSING_TOKEN("Missing Token"),
    ACCOUNT_NOT_FOUND("Account not found")
    ;

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
