package com.example.account.exception;

public class AccountException extends Exception {
    private final ExceptionCode code;

    public AccountException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public AccountException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}
