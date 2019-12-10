package com.example.stockdata.api.exception;

public class ShareDataException extends Exception {

    private final ExceptionCode code;

    public ShareDataException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ShareDataException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}
