package com.example.stockdata.api.rest.converter

import com.example.stockdata.api.model.ShareDataModel
import com.example.stockdata.api.rest.exception.ExceptionCode
import com.example.stockdata.api.spec.model.ExceptionResponse
import com.example.stockdata.api.spec.model.ShareDataResponse
import java.util.function.Function

class PriceExceptionConverter {
    var toResponse = Function { model: ShareDataModel ->
        ShareDataResponse.newBuilder()
                .setPrice(model.price)
                .setSymbol(model.symbol)
                .setLastUpdatedOn(model.lastUpdatedOn.toString())
                .build()
    }


    companion object {
        fun toExceptionCode(model: ExceptionCode): ExceptionResponse.ExceptionCode {
            return when(model) {
                ExceptionCode.BAD_REQUEST -> ExceptionResponse.ExceptionCode.BAD_REQUEST
                ExceptionCode.NO_DATA -> ExceptionResponse.ExceptionCode.NO_DATA
                else -> ExceptionResponse.ExceptionCode.UNKNOWN
            }
        }
    }
}
