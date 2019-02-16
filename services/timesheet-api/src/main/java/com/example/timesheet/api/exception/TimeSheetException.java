package com.example.timesheet.api.exception;

public class TimeSheetException extends Exception {
    private final ExceptionCode code;

    public TimeSheetException(ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public TimeSheetException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}
