package com.example.gateway.api.exception;

public enum ExceptionCode {
    UNKNOWN("Unknown"),
    API_NOT_RESPONDING("Api not responding"),
    TENANT_NOT_FOUND("Tenant not found"),
    ACCOUNT_NOT_FOUND("Account not found"),
    TIMESHEET_EMPTY("No timesheet entries found"),
    REQUEST_NOT_AUTHORIZED("Request not authorized"),
    TRADEJOURNAL_EMPTY("No trades found"),
    TRADEJOURNAL_CANNOT_DELETE("Cannot delete"),
    TRADEJOURNAL_NO_SYMBOLS("No symbols found"),
    RESOURCE_NOT_FOUND("Resource not found")
    ;

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
