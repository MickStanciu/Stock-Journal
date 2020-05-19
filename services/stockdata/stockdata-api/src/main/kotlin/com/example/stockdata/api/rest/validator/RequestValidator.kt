package com.example.stockdata.api.rest.validator

import com.example.common.validator.FieldValidator

class RequestValidator: FieldValidator() {

    companion object {
        fun validateGetPriceForSymbol(symbol: String): Boolean {
            return FieldValidator.symbol(symbol)
        }

        fun validateGetOldestSymbols(limit: Int): Boolean {
            return FieldValidator.limit(limit)
        }
    }
}