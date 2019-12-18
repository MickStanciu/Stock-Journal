package com.example.tradelog.api.rest.controller

import com.example.tradelog.api.core.service.TransactionService
import com.example.tradelog.api.rest.exception.ExceptionCode
import com.example.tradelog.api.rest.exception.TradeLogException
import com.example.tradelog.api.rest.validator.RequestValidator
import com.example.tradelog.api.spec.model.ActiveSymbolsResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/v1/transactions"])
class TransactionController(private val transactionService: TransactionService) {

    @RequestMapping(value = ["/symbols"], method = [RequestMethod.GET], produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getAllTradedSymbols(@RequestHeader("accountId") accountId: String): ActiveSymbolsResponse {

        if (!RequestValidator.validateGetAllTradedSymbols(accountId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        val symbols = transactionService.getAllTradedSymbols(accountId)

        return ActiveSymbolsResponse.newBuilder()
                .addAllSymbols(symbols)
                .build()
    }

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }
}