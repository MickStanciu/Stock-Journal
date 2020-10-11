package com.example.gateway.api.rest.exception

import com.example.common.converter.TimeConverter
import com.example.common.exception.ApiException
import com.example.common.exception.ApiExceptionCode
import com.example.gateway.api.rest.converter.toExceptionCode
import com.example.gateway.api.spec.model.ExceptionResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.ResourceAccessException
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
                .setCode(ExceptionResponse.ExceptionCode.INTERNAL_ERROR)
                .setMessage("Critical Internal Error")
                .setDetails("")
                .setTimestamp(TimeConverter.getOffsetDateTimeNow().toString())
                .build()
        LOG.error(ex.message, ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse)
    }

    @ExceptionHandler(ResourceAccessException::class)
    fun handleResourceAccessExceptions(ex: ResourceAccessException, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse.newBuilder()
                .setCode(ExceptionResponse.ExceptionCode.INTERNAL_ERROR)
                .setMessage("Critical Internal Error")
                .setDetails("")
                .setTimestamp(TimeConverter.getOffsetDateTimeNow().toString())
                .build()
        LOG.error(ex.message, ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse)
    }

    @ExceptionHandler(GatewayApiException::class)
    fun handleTradeLogExceptions(ex: GatewayApiException, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse.newBuilder()
                .setCode(toExceptionCode(ex.code))
                .setMessage(ex.message)
                .setDetails(request.getDescription(false))
                .setTimestamp(TimeConverter.getOffsetDateTimeNow().toString())
                .build()
        LOG.error(ex.message, ex)
        return when (ex.code) {
            ExceptionCode.BAD_REQUEST -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)
            ExceptionCode.REQUEST_NOT_AUTHORIZED -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse)
            ExceptionCode.ACCOUNT_NOT_FOUND -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse)

//            ExceptionCode.CREATE_SHARE_FAILED -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)
//            ExceptionCode.EDIT_SHARE_FAILED -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)
//            ExceptionCode.DELETE_SHARE_FAILED -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)
//
//            ExceptionCode.CREATE_OPTION_FAILED -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)
//            ExceptionCode.EDIT_OPTION_FAILED -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)
//            ExceptionCode.DELETE_OPTION_FAILED -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)
//
//            ExceptionCode.CREATE_DIVIDEND_FAILED -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)
//            ExceptionCode.DELETE_DIVIDEND_FAILED -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)
//
//            ExceptionCode.UPDATE_TRANSACTION_OPTIONS_FAILED -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse)

            else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse)
        }
    }

    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse.newBuilder()
                .setCode(toExceptionCode(ex.code))
                .setMessage(ex.message)
                .setDetails(request.getDescription(false))
                .setTimestamp(TimeConverter.getOffsetDateTimeNow().toString())
                .build()

        LOG.error(ex.message, ex)
        return when (ex.code) {
            ApiExceptionCode.EXTERNAL_API_DATA_CONVERSION_ERROR -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(exceptionResponse)
            ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse)
            else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse)
        }
    }


}
