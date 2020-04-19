package com.example.tradelog.api.rest

import com.example.tradelog.api.spec.model.TLPortfolioResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus

/*
    Note: the problem with this interface: depends on spring, but makes controller more pretty
 */
interface PortfolioRestInterface {
    companion object {
        private const val ACCOUNT_ID_HEADER_NAME = "x-account-id"
    }

    @RequestMapping(value = ["/", ""], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getPortfolios(@RequestHeader(ACCOUNT_ID_HEADER_NAME) accountId: String): TLPortfolioResponse
}