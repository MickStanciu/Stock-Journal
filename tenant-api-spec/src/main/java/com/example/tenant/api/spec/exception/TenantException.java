package com.example.tenant.api.spec.exception;

public class TenantException extends Exception {
    private final ExceptionCode code;

    public TenantException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public TenantException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}
