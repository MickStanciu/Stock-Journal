package com.example.stockdata.api.rest.converter

import com.example.common.exception.ApiException
import com.example.common.exception.ApiExceptionCode
import com.example.stockdata.api.spec.model.ExceptionResponse
import org.slf4j.LoggerFactory

class ApiExceptionConverter {

    companion object {
        fun toExceptionCode(ex: ApiException): ExceptionResponse.ExceptionCode {
            return when(ex.code) {
                ApiExceptionCode.DATABASE_RECORD_NOT_FOUND -> ExceptionResponse.ExceptionCode.DATABASE_RECORD_NOT_FOUND
                ApiExceptionCode.DATABASE_ACCESS_ERROR -> ExceptionResponse.ExceptionCode.DATABASE_ACCESS_ERROR
                ApiExceptionCode.DATABASE_MORE_THAN_ONE_RECORD -> ExceptionResponse.ExceptionCode.DATABASE_MORE_THAN_ONE_RECORD
                else -> {
                    LOG.error("Cannot map ${ex.code}")
                    ExceptionResponse.ExceptionCode.UNKNOWN
                }
            }
        }

        private val LOG = LoggerFactory.getLogger(ApiExceptionConverter::class.java)
    }
}
