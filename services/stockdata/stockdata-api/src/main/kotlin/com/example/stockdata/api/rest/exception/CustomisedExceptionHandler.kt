package com.example.stockdata.api.rest.exception

import com.example.common.converter.TimeConverter
import com.example.common.exception.ApiException
import com.example.common.exception.ApiExceptionCode
import com.example.stockdata.api.rest.converter.ApiExceptionConverter
import com.example.stockdata.api.spec.model.ExceptionResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomisedExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse.newBuilder()
                .setCode(ExceptionResponse.ExceptionCode.UNKNOWN)
                .setMessage(ex.message)
                .setDetails(request.getDescription(false))
                .setTimestamp(TimeConverter.getOffsetDateTimeNow().toString())
                .build()
        LOG.error(ex.message, ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse)
    }

    @ExceptionHandler(PriceException::class)
    fun handlePriceException(ex: PriceException, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponseBuilder = ExceptionResponse.newBuilder()
                .setCode(ExceptionResponse.ExceptionCode.DATABASE_ACCESS_ERROR)
                .setMessage(ex.message)
                .setDetails(request.getDescription(false))
                .setTimestamp(TimeConverter.getOffsetDateTimeNow().toString())
        LOG.error(ex.message, ex)
        return when (ex.code) {
            ExceptionCode.BAD_REQUEST -> {
                val exceptionResponse = exceptionResponseBuilder.setCode(ExceptionResponse.ExceptionCode.DATABASE_RECORD_NOT_FOUND).build()
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)
            }
            ExceptionCode.NO_DATA -> {
                val exceptionResponse = exceptionResponseBuilder.setCode(ExceptionResponse.ExceptionCode.UNKNOWN).build()
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse)
            }
            else -> {
                val exceptionResponse = exceptionResponseBuilder.setCode(ExceptionResponse.ExceptionCode.UNKNOWN).build()
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse)
            }
        }
    }

    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse.newBuilder()
                .setCode(ApiExceptionConverter.toExceptionCode(ex))
                .setMessage(ex.message)
                .setDetails(request.getDescription(false))
                .setTimestamp(TimeConverter.getOffsetDateTimeNow().toString())
                .build()

        LOG.error(ex.message, ex)
        return when (ex.code) {
            ApiExceptionCode.DATABASE_RECORD_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse)
            else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse)
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(CustomisedExceptionHandler::class.java)
    }
}