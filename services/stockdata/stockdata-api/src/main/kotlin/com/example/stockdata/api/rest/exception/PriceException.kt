package com.example.stockdata.api.rest.exception

class PriceException(var code: ExceptionCode) : Exception(code.message) {

}

enum class ExceptionCode(val message: String) {
    UNKNOWN("Unknown"),
    BAD_REQUEST("Bad request"),
    NO_DATA("No data for given request")
}

