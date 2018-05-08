package com.example.core.exception;

public enum ExceptionCode {
    UNKNOWN("Unknown"),
    TIMESHEET_NOT_FOUND("No timesheet records found for given request");

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
