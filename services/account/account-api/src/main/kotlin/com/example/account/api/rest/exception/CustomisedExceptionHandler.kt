package com.example.account.api.rest.exception

import com.example.account.api.rest.converter.toExceptionCode
import com.example.account.api.spec.model.ExceptionResponse
import com.example.common.converter.TimeConverter
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomisedExceptionHandler: ResponseEntityExceptionHandler() {

    companion object {
        private val LOG = LoggerFactory.getLogger(CustomisedExceptionHandler::class.java)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse.newBuilder()
                .setCode(ExceptionResponse.ExceptionCode.UNKNOWN_CODE)
                .setMessage(ex.message)
                .setDetails(request.getDescription(false))
                .setTimestamp(TimeConverter.getOffsetDateTimeNow().toString())
                .build()
        LOG.error(ex.message, ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse)
    }


    @ExceptionHandler(AccountException::class)
    fun handleTradeLogExceptions(ex: AccountException, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse.newBuilder()
                .setCode(toExceptionCode(ex.code))
                .setMessage(ex.message)
                .setDetails(request.getDescription(false))
                .setTimestamp(TimeConverter.getOffsetDateTimeNow().toString())
                .build()
        LOG.error(ex.message, ex)
        return when (ex.code) {
            ExceptionCode.BAD_REQUEST -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)
            ExceptionCode.ACCOUNT_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse)
            else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse)
        }
    }


}