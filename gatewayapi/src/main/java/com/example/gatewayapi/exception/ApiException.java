package com.example.gatewayapi.exception;

class ApiException extends RuntimeException {
    private final ExceptionCode code;

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
