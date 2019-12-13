package com.example.stockdata.api.exception

class PriceException(code: ExceptionCode) : Exception(code.message) {

}

enum class ExceptionCode(val message: String) {
    UNKNOWN("Unknown"),
    BAD_REQUEST("Bad request"),
    SHARE_DATA_EMPTY("No data for given request")
}

