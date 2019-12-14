package com.example.stockdata.api.rest.exception

import com.example.stockdata.api.rest.converter.PriceExceptionConverter
import com.example.stockdata.api.spec.model.ExceptionResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomisedResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse.newBuilder()
                .setCode(ExceptionResponse.ExceptionCode.UNKNOWN)
                .setMessage(ex.message)
                .setDetails(request.getDescription(false))
                .build()
        log.error(ex.message, ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse)
    }

    @ExceptionHandler(PriceException::class)
    fun handlePriceException(ex: PriceException, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse.newBuilder()
                .setCode(PriceExceptionConverter.toExceptionCode(ex.code))
                .setMessage(ex.message)
                .setDetails(request.getDescription(false))
                .build()
        log.error(ex.message, ex)
        return when (ex.code) {
            ExceptionCode.BAD_REQUEST -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)
            ExceptionCode.NO_DATA -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse)
            else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(CustomisedResponseEntityExceptionHandler::class.java)
    }
}