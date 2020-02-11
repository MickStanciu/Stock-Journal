package com.example.gateway.api.rest.controller

import com.example.common.converter.TimeConverter
import com.example.gateway.api.spec.model.ExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/v1/error"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ErrorController {

    @RequestMapping(value = ["/401", "/401/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun error401(): ExceptionResponse {
       return ExceptionResponse.newBuilder()
               .setCode(ExceptionResponse.ExceptionCode.UNAUTHORIZED)
               .setMessage("Invalid api key")
               .setTimestamp(TimeConverter.getOffsetDateTimeNow().toString())
               .build()
    }
}