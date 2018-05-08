package com.example.web.exception;

public class WebException extends Exception {
    private final ExceptionCode code;

    public WebException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public WebException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}
