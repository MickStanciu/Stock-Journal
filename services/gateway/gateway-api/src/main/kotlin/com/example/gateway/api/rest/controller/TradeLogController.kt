package com.example.gateway.api.rest.controller

import com.example.gateway.api.core.service.TradeLogService
import com.example.gateway.api.core.service.TransactionService
import com.example.gateway.api.rest.controller.TradeLogController.Companion.PROTOBUF_MEDIA_TYPE_VALUE
import com.example.gateway.api.rest.converter.*
import com.example.gateway.api.rest.exception.ExceptionCode
import com.example.gateway.api.rest.exception.GatewayApiException
import com.example.gateway.api.spec.model.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
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
            @RequestParam(name = "portfolio-id", required = true) portfolioId: String,
            @PathVariable(name = "symbol", required = true) symbol: String): GWTradeLogDto {

        //todo: validate input
        val support = getSupportData(token)
        val tradeLogModel = tradeLogService.getAll(support.accountId, portfolioId, symbol)

        return TradeLogConverter.toDto(tradeLogModel)
    }

    @RequestMapping(value = ["/summary", "/summary/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getSummary(@RequestHeader(value = "x-auth-key", required = true) token: String): GWTradeSummaryResponse {
        val support = getSupportData(token)
        val summaryModels = tradeLogService.getSummary(support.accountId)

        val dtos = summaryModels.stream().map { TradeSummaryConverter.toDto(it) }
                .collect(Collectors.toList())

        return GWTradeSummaryResponse.newBuilder()
                .addAllItems(dtos)
                .build()
    }

    @RequestMapping(value = ["/summary/matrix", "/summary/matrix/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getSummaryMatrix(@RequestHeader(value = "x-auth-key", required = true) token: String,
                         @RequestParam(name = "portfolio-id", required = true) portfolioId: String
    ): GWSummaryMatrixResponse {
        val support = getSupportData(token)
        val summaryModels = transactionService.getSummaryMatrix(support.accountId, portfolioId)

        val mapDto = SummaryMatrixConverter.toGWDto(summaryModels)

        return GWSummaryMatrixResponse.newBuilder()
                .putAllYears(mapDto)
                .build()
    }


    @RequestMapping(value = ["/shares", "/shares/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createShareTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestParam(name = "portfolio-id", required = true) portfolioId: String,
            @RequestBody dto: GWCreateShareJournalDto): GWShareJournalDto {

        //todo: validate input
        val support = getSupportData(token)
        val createModel = CreateShareJournalConverter.toModel(support.accountId, portfolioId, dto)
        val createdModel = tradeLogService.createShareTransaction(support.accountId, createModel)

        if (createdModel != null) {
            return ShareJournalConverter.toGWDto(createdModel)
        } else {
            throw GatewayApiException(ExceptionCode.CREATE_RESOURCE_FAILED)
        }
    }

    @RequestMapping(value = ["/shares/{transactionId}", "/shares/{transactionId}/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun editShareTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @PathVariable(name = "transactionId", required = true) transactionId: String,
            @RequestBody dto: GWShareJournalDto) {

        //todo: validate input
        val support = getSupportData(token)
        val updateModel = ShareJournalConverter.toModel(support.accountId, dto)
        tradeLogService.editShareTransaction(support.accountId, updateModel)
    }

    @RequestMapping(value = ["/shares/{transactionId}", "/shares/{transactionId}/"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteShareTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @PathVariable(name = "transactionId", required = true) transactionId: String) {

        //todo: validate input
        val support = getSupportData(token)
        tradeLogService.deleteShareTransaction(support.accountId, transactionId)
    }

    @RequestMapping(value = ["/options", "/options/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createOptionTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestParam(name = "portfolio-id", required = true) portfolioId: String,
            @RequestBody dto: GWCreateOptionJournalDto): GWOptionJournalDto {

        //todo: validate input
        val support = getSupportData(token)
        val createModel = CreateOptionJournalConverter.toModel(support.accountId, portfolioId, dto)
        val createdModel = tradeLogService.createOptionTransaction(support.accountId, createModel)

        if (createdModel != null) {
            return OptionJournalConverter.toGWDto(createdModel)
        } else {
            throw GatewayApiException(ExceptionCode.CREATE_RESOURCE_FAILED)
        }
    }

    @RequestMapping(value = ["/options/{transactionId}", "/options/{transactionId}/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun editOptionTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @PathVariable(name = "transactionId", required = true) transactionId: String,
            @RequestBody dto: GWOptionJournalDto) {

        //todo: validate input
        val support = getSupportData(token)
        val updateModel = OptionJournalConverter.toModel(support.accountId, dto)
        tradeLogService.editOptionTransaction(support.accountId, updateModel)
    }

    @RequestMapping(value = ["/options/{transactionId}", "/options/{transactionId}/"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteOptionTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @PathVariable(name = "transactionId", required = true) transactionId: String) {

        //todo: validate input
        val support = getSupportData(token)
        tradeLogService.deleteOptionTransaction(support.accountId, transactionId)
    }

    @RequestMapping(value = ["/dividends", "/dividends/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createDividendTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestParam(name = "portfolio-id", required = true) portfolioId: String,
            @RequestBody dto: GWCreateDividendJournalDto): GWDividendJournalDto {

        //todo: validate input
        val support = getSupportData(token)
        val createModel = CreateDividendJournalConverter.toModel(support.accountId, portfolioId, dto)
        val createdModel = tradeLogService.createDividendTransaction(support.accountId, createModel)

        if (createdModel != null) {
            return DividendJournalConverter.toGWDto(createdModel)
        } else {
            throw GatewayApiException(ExceptionCode.CREATE_RESOURCE_FAILED)
        }
    }

    @RequestMapping(value = ["/dividends/{transactionId}", "/dividends/{transactionId}/"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteDividendTransaction(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @PathVariable(name = "transactionId", required = true) transactionId: String) {

        //todo: validate input
        val support = getSupportData(token)
        tradeLogService.deleteDividendTransaction(support.accountId, transactionId)
    }

    @RequestMapping(value = ["/settings/{transactionId}", "/settings/{transactionId}/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun updateSetting(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @PathVariable(name = "transactionId", required = true) transactionId: String,
            @RequestBody dto: GWTransactionSettingsDto) {

        //todo: validate input
        val support = getSupportData(token)
        val model = TransactionSettingsConverter.toModel(dto)
        transactionService.updateTransactionSetting(support.accountId, model)
    }

    @RequestMapping(value = ["/settings/bulk", "/settings/bulk/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun updateSettings(
            @RequestHeader(value = "x-auth-key", required = true) token: String,
            @RequestBody dto: GWTransactionSettingsBulkDto) {

        //todo: validate input
        val support = getSupportData(token)
        val models = TransactionSettingsConverter.toModels(dto)
        transactionService.updateTransactionSettings(support.accountId, models)
    }
}