package com.example.stockdata.api.rest.validator

import com.example.common.validator.FieldValidator

class RequestValidator: FieldValidator() {

    companion object {
        fun validateGetPriceForSymbol(accountId: String, symbol: String): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && FieldValidator.symbol.test(symbol)
        }
    }
}