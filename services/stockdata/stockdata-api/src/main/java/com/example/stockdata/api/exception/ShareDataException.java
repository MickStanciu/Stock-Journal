package com.example.stockdata.api.exception;

public class ShareDataException extends Exception {

    private final XExceptionCode code;

    public ShareDataException(XExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ShareDataException(XExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public XExceptionCode getCode() {
        return code;
    }
}
