package com.example.tradelog.api.rest.controller

import com.example.tradelog.api.core.facade.JournalFacade
import com.example.tradelog.api.rest.controller.TransactionController.Companion.PROTOBUF_MEDIA_TYPE_VALUE
import com.example.tradelog.api.rest.converter.SummaryMatrixConverter
import com.example.tradelog.api.rest.converter.TradeSummaryConverter
import com.example.tradelog.api.rest.converter.TransactionSettingsModelConverter
import com.example.tradelog.api.rest.exception.ExceptionCode
import com.example.tradelog.api.rest.exception.TradeLogException
import com.example.tradelog.api.rest.validator.RequestValidator
import com.example.tradelog.api.spec.model.TLActiveSymbolsResponse
import com.example.tradelog.api.spec.model.TLSummaryMatrixResponse
import com.example.tradelog.api.spec.model.TLTradeSummaryResponse
import com.example.tradelog.api.spec.model.TLTransactionSettingsDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/v1/transactions"], produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class TransactionController(private val journalFacade: JournalFacade) {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
        private val LOG = LoggerFactory.getLogger(TransactionController::class.java)
    }

    @RequestMapping(value = ["/symbols"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getAllTradedSymbols(@RequestHeader("accountId") accountId: String): TLActiveSymbolsResponse {

        if (!RequestValidator.validateGetAllTradedSymbols(accountId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        val symbols = journalFacade.getAllTradedSymbols(accountId)

        return TLActiveSymbolsResponse.newBuilder()
                .addAllSymbols(symbols)
                .build()
    }

    /*
        Return the unique symbols that have been traded during last year
    */
    @RequestMapping(value = ["/active-symbols"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getAllActiveSymbols(): TLActiveSymbolsResponse {

        val symbols = journalFacade.getActiveSymbols()

        return TLActiveSymbolsResponse.newBuilder()
                .addAllSymbols(symbols)
                .build()
    }


    @RequestMapping(value = ["/summary"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getSummary(@RequestHeader("accountId") accountId: String): TLTradeSummaryResponse {

        if (!RequestValidator.validateGetSummary(accountId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        val summaryList = journalFacade.getSummary(accountId)
        return TradeSummaryConverter.toTradeSummaryResponse(summaryList)
    }

    @RequestMapping(value = ["/summary/matrix", "/summary/matrix/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getSummaryMatrix(@RequestHeader("accountId") accountId: String): TLSummaryMatrixResponse {
        if (!RequestValidator.validateSummaryMatrix(accountId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        val summaryList = journalFacade.getSummaryMatrix(accountId)
        return SummaryMatrixConverter.toSummaryMatrixResponse(summaryList)
    }


    @RequestMapping(value = ["/settings/{transactionId}"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun updateSettings(@RequestHeader("accountId") accountId: String,
                       @PathVariable("transactionId") transactionId: String,
                       @RequestBody dto: TLTransactionSettingsDto) {

        if (!RequestValidator.validateUpdateSettings(accountId, transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        if (!journalFacade.updateSettings(TransactionSettingsModelConverter.toModel(dto))) {
            LOG.error("Could not update settings for transaction: $transactionId")
            throw TradeLogException(ExceptionCode.UPDATE_TRANSACTION_OPTIONS_FAILED)
        }
    }
}
