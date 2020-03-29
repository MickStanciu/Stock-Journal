package com.example.account.api.rest.converter

import com.example.account.api.rest.exception.ExceptionCode
import com.example.account.api.spec.model.ExceptionResponse

fun toExceptionCode(model: ExceptionCode): ExceptionResponse.ExceptionCode {
    return when(model) {
        ExceptionCode.BAD_REQUEST -> ExceptionResponse.ExceptionCode.BAD_REQUEST
        else -> ExceptionResponse.ExceptionCode.UNKNOWN_CODE
    }
}