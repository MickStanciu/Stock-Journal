package com.example.web.exception;

public enum ExceptionCode {
    UNKNOWN("Unknown"),
    MISSING_COOKIE("Missing Cookie"),
    MISSING_TOKEN("Missing Token")
    ;

    private String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
