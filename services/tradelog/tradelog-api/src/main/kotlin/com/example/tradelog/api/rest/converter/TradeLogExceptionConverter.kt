package com.example.tradelog.api.rest.converter

import com.example.tradelog.api.rest.exception.ExceptionCode
import com.example.tradelog.api.spec.model.ExceptionResponse

fun toExceptionCode(model: ExceptionCode): ExceptionResponse.ExceptionCode {
    return when(model) {
        ExceptionCode.BAD_REQUEST -> ExceptionResponse.ExceptionCode.BAD_REQUEST
        else -> ExceptionResponse.ExceptionCode.UNKNOWN_CODE
    }
}