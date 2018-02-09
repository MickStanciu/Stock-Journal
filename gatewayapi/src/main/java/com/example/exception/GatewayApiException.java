package com.example.exception;

public class GatewayApiException extends Exception {
    private ExceptionCode code;

    public GatewayApiException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public GatewayApiException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}
