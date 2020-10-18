package com.example.gateway.api.rest.controller

import arrow.core.getOrHandle
import com.example.gateway.api.core.service.TradeLogService
import com.example.gateway.api.core.service.TransactionService
import com.example.gateway.api.rest.controller.TradeLogController.Companion.PROTOBUF_MEDIA_TYPE_VALUE
import com.example.gateway.api.rest.converter.*
import com.example.gateway.api.spec.model.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors

@RestController
@RequestMapping(value = ["/api/v1/tradelog"], produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class TradeLogController(private val tradeLogService: TradeLogService,
                         private val transactionService: TransactionService): SecureController {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }


    @RequestMapping(value = ["/all/{symbol}", "/all/{symbol}/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getAll(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID,
            @PathVariable(name = "symbol", required = true) symbol: String): GWTradeLogDto {

        //todo: validate input
        val support = getSupportData(token, portfolioId)
        val tradeLogModel = tradeLogService.getAll(support, symbol)

        return TradeLogConverter.toDto(tradeLogModel)
    }

    @RequestMapping(value = ["/summary", "/summary/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getSummary(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID
    ): GWTradeSummaryResponse {
        val support = getSupportData(token, portfolioId)
        val summaryModels = tradeLogService.getSummary(support)

        val dtos = summaryModels
                .getOrHandle { throw it }
                .stream().map { TradeSummaryConverter.toDto(it) }
                .collect(Collectors.toList())

        return GWTradeSummaryResponse.newBuilder()
                .addAllItems(dtos)
                .build()
    }

    @RequestMapping(value = ["/summary/matrix", "/summary/matrix/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getSummaryMatrix(@RequestHeader(value = "x-auth-key", required = true) token: String,
                         @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID,
                         @RequestParam(name = "shares-only", required = false, defaultValue = "false") sharesOnly: Boolean

    ): GWSummaryMatrixResponse {
        val support = getSupportData(token, portfolioId)
        val summaryModels = transactionService
                .getSummaryMatrix(support, sharesOnly)
                .getOrHandle { throw it }

        val mapDto = SummaryMatrixConverter.toGWDto(summaryModels)

        return GWSummaryMatrixResponse.newBuilder()
                .putAllYears(mapDto)
                .build()
    }


    @RequestMapping(value = ["/shares", "/shares/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createShareTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID,
            @RequestBody dto: GWCreateShareJournalDto): GWShareJournalDto {

        //todo: validate input
        val support = getSupportData(token, portfolioId)
        val createModel = CreateShareJournalConverter.toModel(support, dto)
        val createdModel = tradeLogService
                .createShareTransaction(support, createModel)
                .getOrHandle { throw it }

        return ShareJournalConverter.toGWDto(createdModel)
    }

    @RequestMapping(value = ["/shares/{transactionId}", "/shares/{transactionId}/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun editShareTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID,
            @PathVariable(name = "transactionId", required = true) transactionId: String,
            @RequestBody dto: GWShareJournalDto) {

        //todo: validate input
        val support = getSupportData(token, portfolioId)
        val updateModel = ShareJournalConverter.toModel(support, dto)
        tradeLogService
                .editShareTransaction(support, updateModel)
                .getOrHandle { throw it }
    }

    @RequestMapping(value = ["/shares/{transactionId}", "/shares/{transactionId}/"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteShareTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID,
            @PathVariable(name = "transactionId", required = true) transactionId: String) {

        //todo: validate input
        val support = getSupportData(token, portfolioId)
        tradeLogService
                .deleteShareTransaction(support, transactionId)
                .getOrHandle { throw it }
    }

    @RequestMapping(value = ["/options", "/options/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createOptionTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID,
            @RequestBody dto: GWCreateOptionJournalDto): GWOptionJournalDto {

        //todo: validate input
        val support = getSupportData(token, portfolioId)
        val createModel = CreateOptionJournalConverter.toModel(support, dto)
        val createdModel = tradeLogService
                .createOptionTransaction(support, createModel)
                .getOrHandle { throw it }

        return OptionJournalConverter.toGWDto(createdModel)
    }

    @RequestMapping(value = ["/options/{transactionId}", "/options/{transactionId}/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun editOptionTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID,
            @PathVariable(name = "transactionId", required = true) transactionId: String,
            @RequestBody dto: GWOptionJournalDto) {

        //todo: validate input
        val support = getSupportData(token, portfolioId)
        val updateModel = OptionJournalConverter.toModel(support, dto)
        tradeLogService
                .editOptionTransaction(support, updateModel)
                .getOrHandle { throw it }
    }

    @RequestMapping(value = ["/options/{transactionId}", "/options/{transactionId}/"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteOptionTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID,
            @PathVariable(name = "transactionId", required = true) transactionId: String) {

        //todo: validate input
        val support = getSupportData(token, portfolioId)
        tradeLogService
                .deleteOptionTransaction(support, transactionId)
                .getOrHandle { throw it }
    }

    @RequestMapping(value = ["/dividends", "/dividends/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createDividendTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID,
            @RequestBody dto: GWCreateDividendJournalDto): GWDividendJournalDto {

        //todo: validate input
        val support = getSupportData(token, portfolioId)
        val createModel = CreateDividendJournalConverter.toModel(support, dto)
        val createdModel = tradeLogService
                .createDividendTransaction(support, createModel)
                .getOrHandle { throw it }

        return DividendJournalConverter.toGWDto(createdModel)
    }

    @RequestMapping(value = ["/dividends/{transactionId}", "/dividends/{transactionId}/"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteDividendTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID,
            @PathVariable(name = "transactionId", required = true) transactionId: String) {

        //todo: validate input
        val support = getSupportData(token, portfolioId)
        tradeLogService
                .deleteDividendTransaction(support, transactionId)
                .getOrHandle { throw it }
    }

    @RequestMapping(value = ["/settings/{transactionId}", "/settings/{transactionId}/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun updateSetting(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID,
            @PathVariable(name = "transactionId", required = true) transactionId: String,
            @RequestBody dto: GWTransactionSettingsDto) {

        //todo: validate input
        val support = getSupportData(token, portfolioId)
        val model = TransactionSettingsConverter.toModel(dto)
        transactionService
                .updateTransactionSetting(support, model)
                .getOrHandle { throw it }
    }

    @RequestMapping(value = ["/settings/bulk", "/settings/bulk/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun updateSettings(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestHeader(name = "portfolio-id", required = true) portfolioId: UUID,
            @RequestBody dto: GWTransactionSettingsBulkDto) {

        //todo: validate input
        val support = getSupportData(token, portfolioId)
        val models = TransactionSettingsConverter.toModels(dto)
        transactionService
                .updateTransactionSettings(support, models)
                .getOrHandle { throw it }
    }
}