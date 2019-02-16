package com.example.account.api.exception;


import com.example.account.api.spec.exception.ExceptionCode;

public class ResourceErrorException extends RuntimeException {
    private final ExceptionCode code;

    public ResourceErrorException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ResourceErrorException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}
