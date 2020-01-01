package com.example.tradelog.api.rest.exception

class TradeLogException(var code: ExceptionCode): Exception(code.message)

enum class ExceptionCode(val message: String) {
    UNKNOWN("Unknown"),
    BAD_REQUEST("Bad request"),
    TRADELOG_EMPTY("Tradelog is empty"),
    SHARE_DATA_EMPTY("No data for given request"),
    CREATE_SHARE_FAILED("Could not create share record"),
    DELETE_SHARE_FAILED("Could not delete share record"),
    EDIT_SHARE_FAILED("Could not edit share record"),
    CREATE_OPTION_FAILED("Could not create option record"),
    EDIT_OPTION_FAILED("Could not edit option record"),
    DELETE_OPTION_FAILED("Could not delete option record"),
    CREATE_DIVIDEND_FAILED("Could not create dividend record"),
    EDIT_DIVIDEND_FAILED("Could not edit dividend record"),
    DELETE_DIVIDEND_FAILED("Could not delete dividend record"),
    UPDATE_TRANSACTION_OPTIONS_FAILED("Could not update transaction options record")
}
