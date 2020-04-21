package com.example.tradelog.api.rest

import com.example.tradelog.api.spec.model.TLOptionJournalDto
import com.example.tradelog.api.spec.model.TLOptionTransactionsResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/*
    Note: the problem with this interface: depends on spring, but makes controller more pretty
 */
interface OptionJournalRestInterface {
    companion object {
        private const val ACCOUNT_ID_HEADER_NAME = "x-account-id"
    }

    @RequestMapping(value = ["/{symbol}/{portfolio-id}", "/{symbol}/{portfolio-id}/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getAllBySymbol(
            @RequestHeader(ACCOUNT_ID_HEADER_NAME) accountId: String,
            @PathVariable("symbol") symbol: String,
            @PathVariable("portfolio-id") portfolioId: String) : TLOptionTransactionsResponse

    @RequestMapping(value = ["", "/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createRecord(
            @RequestHeader(ACCOUNT_ID_HEADER_NAME) accountId: String,
            @RequestBody dto: TLOptionJournalDto): TLOptionJournalDto

    @RequestMapping(value = ["/{transactionId}", "/{transactionId}/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun editRecord(
            @RequestHeader(ACCOUNT_ID_HEADER_NAME) accountId: String,
            @PathVariable("transactionId") transactionId: String,
            @RequestBody dto: TLOptionJournalDto)

    @RequestMapping(value = ["/{transactionId}", "/{transactionId}/"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteRecord(@RequestHeader(ACCOUNT_ID_HEADER_NAME) accountId: String,
                     @PathVariable("transactionId") transactionId: String)
}