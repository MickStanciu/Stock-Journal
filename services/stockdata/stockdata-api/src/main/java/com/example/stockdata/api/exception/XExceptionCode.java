package com.example.stockdata.api.exception;

public enum XExceptionCode {
    UNKNOWN("Unknown"),
    BAD_REQUEST("Bad request"),
    SHARE_DATA_EMPTY("No data for given request"),
    ;

    private final String message;

    XExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
