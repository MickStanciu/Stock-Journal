package com.example.tradelog.api.exception;

import com.example.tradelog.api.spec.exception.ExceptionCode;

public class TradeLogException extends Exception {

    private final ExceptionCode code;

    public TradeLogException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public TradeLogException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}
