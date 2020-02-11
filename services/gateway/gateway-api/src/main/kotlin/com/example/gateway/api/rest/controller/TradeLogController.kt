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

//    @RequestMapping(value = ["/symbols", "/symbols/"], method = [RequestMethod.GET])
//    @ResponseStatus(HttpStatus.OK)
//    fun getAllTradedSymbols(@RequestHeader("auth-key") token: String): ActiveSymbolsResponse {
//
//        //todo: validate input
//
//        val tradedSymbols: List<String> = tradeLogService.getAllTradedSymbols(accountId)
//
//        return ActiveSymbolsResponse.newBuilder()
//                .addAllSymbols(tradedSymbols)
//                .build()
//    }

    @RequestMapping(value = ["/all/{symbol}", "/all/{symbol}/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getAll(
            @RequestHeader("auth-key") token: String,
            @PathVariable(name = "symbol") symbol: String): GWTradeLogDto {

        //todo: validate input
        val support = getSupportData(token)
        val tradeLogModel = tradeLogService.getAll(support.accountId, symbol)

        return TradeLogConverter.toDto(tradeLogModel)
    }

    @RequestMapping(value = ["/summary", "/summary/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getSummary(@RequestHeader("auth-key") token: String): GWTradeSummaryResponse {
        val support = getSupportData(token)
        val summaryModels = tradeLogService.getSummary(support.accountId)

        val dtos = summaryModels.stream().map { TradeSummaryConverter.toDto(it) }
                .collect(Collectors.toList())

        return GWTradeSummaryResponse.newBuilder()
                .addAllItems(dtos)
                .build();
    }

    @RequestMapping(value = ["/shares", "/shares/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createShareTransaction(
            @RequestHeader("auth-key") token: String,
            @RequestBody dto: GWCreateShareJournalDto): GWShareJournalDto {

        //todo: validate input
        val support = getSupportData(token)
        val createModel = CreateShareJournalConverter.toModel(support.accountId, dto)
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
            @RequestHeader("auth-key") token: String,
            @PathVariable(name = "transactionId") transactionId: String,
            @RequestBody dto: GWShareJournalDto) {

        //todo: validate input
        val support = getSupportData(token)
        val updateModel = ShareJournalConverter.toModel(support.accountId, dto)
        tradeLogService.editShareTransaction(support.accountId, updateModel)
    }

    @RequestMapping(value = ["/shares/{transactionId}", "/shares/{transactionId}/"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteShareTransaction(
            @RequestHeader("auth-key") token: String,
            @PathVariable(name = "transactionId") transactionId: String) {

        //todo: validate input
        val support = getSupportData(token)
        tradeLogService.deleteShareTransaction(support.accountId, transactionId)
    }

    @RequestMapping(value = ["/options", "/options/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createOptionTransaction(
            @RequestHeader("auth-key") token: String,
            @RequestBody dto: GWCreateOptionJournalDto): GWOptionJournalDto {

        //todo: validate input
        val support = getSupportData(token)
        val createModel = CreateOptionJournalConverter.toModel(support.accountId, dto)
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
            @RequestHeader("auth-key") token: String,
            @PathVariable(name = "transactionId") transactionId: String,
            @RequestBody dto: GWOptionJournalDto) {

        //todo: validate input
        val support = getSupportData(token)
        val updateModel = OptionJournalConverter.toModel(support.accountId, dto)
        tradeLogService.editOptionTransaction(support.accountId, updateModel)
    }

    @RequestMapping(value = ["/options/{transactionId}", "/options/{transactionId}/"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteOptionTransaction(
            @RequestHeader("auth-key") token: String,
            @PathVariable(name = "transactionId") transactionId: String) {

        //todo: validate input
        val support = getSupportData(token)
        tradeLogService.deleteOptionTransaction(support.accountId, transactionId)
    }

    @RequestMapping(value = ["/dividends", "/dividends/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createDividendTransaction(
            @RequestHeader("auth-key") token: String,
            @RequestBody dto: GWCreateDividendJournalDto): GWDividendJournalDto {

        //todo: validate input
        val support = getSupportData(token)
        val createModel = CreateDividendJournalConverter.toModel(support.accountId, dto)
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
            @RequestHeader("auth-key") token: String,
            @PathVariable(name = "transactionId") transactionId: String) {

        //todo: validate input
        val support = getSupportData(token)
        tradeLogService.deleteDividendTransaction(support.accountId, transactionId)
    }

    @RequestMapping(value = ["/settings/{transactionId}", "/settings/{transactionId}/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun updateSetting(
            @RequestHeader("auth-key") token: String,
            @PathVariable(name = "transactionId") transactionId: String,
            @RequestBody dto: GWTransactionSettingsDto) {

        //todo: validate input
        val support = getSupportData(token)
        val model = TransactionSettingsConverter.toModel(dto)
        transactionService.updateTransactionSetting(support.accountId, model)
    }

    @RequestMapping(value = ["/settings/bulk", "/settings/bulk/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun updateSettings(
            @RequestHeader("auth-key") token: String,
            @RequestBody dto: GWTransactionSettingsBulkDto) {

        //todo: validate input
        val support = getSupportData(token)
        val models = TransactionSettingsConverter.toModels(dto)
        transactionService.updateTransactionSettings(support.accountId, models)
    }
}