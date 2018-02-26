package com.example.gatewayapi.exception;

public class ApiException extends RuntimeException {
    private ExceptionCode code;

    public ApiException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ApiException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}
