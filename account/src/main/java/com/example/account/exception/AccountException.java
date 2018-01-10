package com.example.account.exception;

public class AccountException extends Exception {
    private ExceptionCode code;

    public AccountException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}
