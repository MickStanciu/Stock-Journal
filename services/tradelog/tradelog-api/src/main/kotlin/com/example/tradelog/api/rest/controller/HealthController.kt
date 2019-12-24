package com.example.tradelog.api.rest.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/v1/health"], produces = [HealthController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class HealthController {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
        private val LOG = LoggerFactory.getLogger(HealthController::class.java)
    }

    @RequestMapping(value = ["/ping", "/ping/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun ping(): String {
        return "pong"
    }
}
