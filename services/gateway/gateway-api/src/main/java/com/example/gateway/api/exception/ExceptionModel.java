package com.example.gateway.api.exception;

import java.time.LocalDateTime;

public class ExceptionModel {
    private LocalDateTime timestamp;
    private ExceptionCode code;
    private String message;
    private String details;

    public ExceptionModel(ExceptionCode code) {
        this.code = code;
        this.message = code.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public ExceptionModel(ExceptionCode code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ExceptionModel(ExceptionCode code, String message, String details) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ExceptionCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
