package com.example.gateway.api.rest.exception

class GatewayApiException(var code: ExceptionCode): Exception(code.message)

enum class ExceptionCode(val message: String) {
    UNKNOWN("Unknown"),
    BAD_REQUEST("Bad request"),
    CREATE_RESOURCE_FAILED("Could not create resouce"),
    API_NOT_RESPONDING("Api not responding"),
    SHARE_DATA_NOT_FOUND("Share data not found"),
    TENANT_NOT_FOUND("Tenant not found"),
    ACCOUNT_NOT_FOUND("Account not found"),
    TIMESHEET_EMPTY("No timesheet entries found"),
    REQUEST_NOT_AUTHORIZED("Request not authorized"),
    TRADEJOURNAL_EMPTY("No trades found"),
    TRADEJOURNAL_CANNOT_DELETE("Cannot delete"),
    TRADEJOURNAL_NO_SYMBOLS("No symbols found"),
    RESOURCE_NOT_FOUND("Resource not found"),
    TOKEN_FAIL("Token failed to decode"),
    NO_DEFAULT_PORTFOLIO("No default portfolio found")
}

