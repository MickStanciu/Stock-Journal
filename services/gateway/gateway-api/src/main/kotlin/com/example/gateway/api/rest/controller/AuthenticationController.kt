package com.example.gateway.api.rest.controller

import arrow.core.getOrHandle
import com.example.gateway.api.core.service.AccountService
import com.example.gateway.api.core.service.TradeLogService
import com.example.gateway.api.rest.controller.AuthenticationController.Companion.PROTOBUF_MEDIA_TYPE_VALUE
import com.example.gateway.api.rest.converter.TokenConverter
import com.example.gateway.api.rest.exception.ExceptionCode
import com.example.gateway.api.rest.exception.GatewayApiException
import com.example.gateway.api.rest.validator.RequestValidator
import com.example.gateway.api.spec.model.GWAuthRequestDto
import com.example.gateway.api.spec.model.GWAuthResponseDto
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/v1/auth"], produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class AuthenticationController(private val accountService: AccountService,
                               private val tradeLogService: TradeLogService) {


    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    @RequestMapping(value = ["", "/"], method = [RequestMethod.POST])
    fun authenticate(@RequestBody authRequestDto: GWAuthRequestDto): GWAuthResponseDto {
        if (!RequestValidator.validateAuthenticate(authRequestDto.loginName, authRequestDto.password)) {
            throw GatewayApiException(ExceptionCode.BAD_REQUEST)
        }

        val activeAccount = accountService
                .getActiveAccount(authRequestDto.loginName, authRequestDto.password)
                .getOrHandle { throw it }
        val token = TokenConverter.encode(activeAccount.id)

        val defaultPortfolio = tradeLogService
                .getDefaultPortfolio(accountId = activeAccount.id)
                .getOrHandle { throw it }

        return GWAuthResponseDto.newBuilder()
                .setApiToken(token)
                .setAccountId(activeAccount.id)
                .setDisplayName(activeAccount.displayName)
                .setEmail(activeAccount.email)
                .setLoginName(activeAccount.loginName)
                .setPortfolioId(defaultPortfolio.id)
                .setPortfolioName(defaultPortfolio.name)
                .build()
    }
}