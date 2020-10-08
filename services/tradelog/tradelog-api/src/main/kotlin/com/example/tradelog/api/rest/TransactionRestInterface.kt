package com.example.tradelog.api.rest

import com.example.tradelog.api.spec.model.TLActiveSymbolsResponse
import com.example.tradelog.api.spec.model.TLSummaryMatrixResponse
import com.example.tradelog.api.spec.model.TLTradeSummaryResponse
import com.example.tradelog.api.spec.model.TLTransactionSettingsDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/*
    Note: the problem with this interface: depends on spring, but makes controller more pretty
 */
interface TransactionRestInterface {
    companion object {
        private const val ACCOUNT_ID_HEADER_NAME = "x-account-id"
    }

    @RequestMapping(value = ["/active-symbols"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getAllActiveSymbols(): TLActiveSymbolsResponse

    @RequestMapping(value = ["/summary"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getSummary(@RequestHeader(ACCOUNT_ID_HEADER_NAME, required = true) accountId: String): TLTradeSummaryResponse

    @RequestMapping(value = ["/summary/matrix/{portfolio-id}", "/summary/matrix/{portfolio-id}/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getSummaryMatrix(@RequestHeader(ACCOUNT_ID_HEADER_NAME, required = true) accountId: String,
                         @PathVariable("portfolio-id") portfolioId: String,
                         @RequestParam(name = "shares-only", required = false, defaultValue = "false") sharesOnly: Boolean
    ): TLSummaryMatrixResponse

    @RequestMapping(value = ["/settings/{transactionId}"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun updateSettings(@RequestHeader(ACCOUNT_ID_HEADER_NAME, required = true) accountId: String,
                       @PathVariable("transaction-id") transactionId: String,
                       @RequestBody dto: TLTransactionSettingsDto)
}