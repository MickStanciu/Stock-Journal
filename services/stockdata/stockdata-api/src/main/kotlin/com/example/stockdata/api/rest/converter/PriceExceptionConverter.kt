package com.example.stockdata.api.rest.converter

import com.example.stockdata.api.rest.exception.ExceptionCode
import com.example.stockdata.api.spec.model.ExceptionResponse

class PriceExceptionConverter {

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
