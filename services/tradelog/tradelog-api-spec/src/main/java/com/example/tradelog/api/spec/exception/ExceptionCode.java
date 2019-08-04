package com.example.tradelog.api.spec.exception;

public enum ExceptionCode {
    UNKNOWN("Unknown"),
    BAD_REQUEST("Bad request"),
    TRADELOG_EMPTY("Tradelog is empty"),
    SHARE_DATA_EMPTY("No data for given request"),
    CREATE_SHARE_FAILED("Could not create share record"),
    DELETE_SHARE_FAILED("Could not delete share record"),
    CREATE_OPTION_FAILED("Could not create option record"),
    DELETE_OPTION_FAILED("Could not delete option record"),
    CREATE_DIVIDEND_FAILED("Could not create dividend record"),
    DELETE_DIVIDEND_FAILED("Could not delete dividend record"),
    UPDATE_TRANSACTION_OPTIONS_FAILED("Could not update transaction options record")
    ;

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
