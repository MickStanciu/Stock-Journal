package com.example.gateway.api.rest.converter

import com.example.common.exception.ApiExceptionCode
import com.example.gateway.api.rest.exception.ExceptionCode
import com.example.gateway.api.spec.model.ExceptionResponse

fun toExceptionCode(model: ExceptionCode): ExceptionResponse.ExceptionCode {
    return when(model) {
        ExceptionCode.BAD_REQUEST -> ExceptionResponse.ExceptionCode.BAD_REQUEST
        else -> ExceptionResponse.ExceptionCode.UNKNOWN_CODE
    }
}

fun toExceptionCode(code: ApiExceptionCode): ExceptionResponse.ExceptionCode {
    return when(code) {
        ApiExceptionCode.EXTERNAL_API_DATA_CONVERSION_ERROR -> ExceptionResponse.ExceptionCode.UNKNOWN_CODE
        ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR -> ExceptionResponse.ExceptionCode.INTERNAL_ERROR
        else -> ExceptionResponse.ExceptionCode.INTERNAL_ERROR
    }
}