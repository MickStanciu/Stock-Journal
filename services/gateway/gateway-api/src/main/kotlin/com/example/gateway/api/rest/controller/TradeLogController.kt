package com.example.gateway.api.rest.controller

import com.example.gateway.api.core.service.TradeLogService
import com.example.gateway.api.core.service.TransactionService
import com.example.gateway.api.exception.ExceptionCode
import com.example.gateway.api.exception.GatewayApiException
import com.example.gateway.api.rest.controller.TradeLogController.Companion.PROTOBUF_MEDIA_TYPE_VALUE
import com.example.gateway.api.rest.converter.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import com.example.gateway.api.spec.model.CreateDividendJournalDto as GWCreateDividendJournalDto
import com.example.gateway.api.spec.model.CreateOptionJournalDto as GWCreateOptionJournalDto
import com.example.gateway.api.spec.model.CreateShareJournalDto as GWCreateShareJournalDto
import com.example.gateway.api.spec.model.DividendJournalDto as GWDividendJournalDto
import com.example.gateway.api.spec.model.OptionJournalDto as GWOptionJournalDto
import com.example.gateway.api.spec.model.ShareJournalDto as GWShareJournalDto
import com.example.gateway.api.spec.model.TradeLogDto as GWTradeLogDto
import com.example.gateway.api.spec.model.TradeSummaryResponse as GWTradeSummaryResponse
import com.example.gateway.api.spec.model.TransactionSettingsDto as GWTransactionSettingsDto

@RestController
@RequestMapping(value = ["/api/v1/tradelog"], produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class TradeLogController(private val tradeLogService: TradeLogService,
                         private val transactionService: TransactionService) {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

//    @RequestMapping(value = ["/symbols", "/symbols/"], method = [RequestMethod.GET])
//    @ResponseStatus(HttpStatus.OK)
//    fun getAllTradedSymbols(@RequestHeader("accountId") accountId: String): ActiveSymbolsResponse {
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
            @RequestHeader("accountId") accountId: String,
            @PathVariable(name = "symbol") symbol: String): GWTradeLogDto {

        //todo: validate input

        val tradeLogModel = tradeLogService.getAll(accountId, symbol)

        return TradeLogConverter.toDto(tradeLogModel)
    }

    @RequestMapping(value = ["/summary", "/summary/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getSummary(@RequestHeader("accountId") accountId: String): GWTradeSummaryResponse {

        //todo: validate input

        val summaryModels = tradeLogService.getSummary(accountId)

        val dtos = summaryModels.stream().map { TradeSummaryConverter.toDto(it) }
                .collect(Collectors.toList())

        return GWTradeSummaryResponse.newBuilder()
                .addAllItems(dtos)
                .build();
    }

    @RequestMapping(value = ["/shares", "/shares/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createShareTransaction(
            @RequestHeader("accountId") accountId: String,
            @RequestBody dto: GWCreateShareJournalDto): GWShareJournalDto {

        //todo: validate input

        val createModel = CreateShareJournalConverter.toModel(accountId, dto)
        val createdModel = tradeLogService.createShareTransaction(accountId, createModel)

        if (createdModel != null) {
            return ShareJournalConverter.toGWDto(createdModel)
        } else {
            throw GatewayApiException(ExceptionCode.CREATE_RESOURCE_FAILED)
        }
    }

    @RequestMapping(value = ["/shares/{transactionId}", "/shares/{transactionId}/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun editShareTransaction(
            @RequestHeader("accountId") accountId: String,
            @PathVariable(name = "transactionId") transactionId: String,
            @RequestBody dto: GWShareJournalDto) {

        //todo: validate input
        val updateModel = ShareJournalConverter.toModel(dto)
        tradeLogService.editShareTransaction(accountId, updateModel)
    }

    @RequestMapping(value = ["/shares/{transactionId}", "/shares/{transactionId}/"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteShareTransaction(
            @RequestHeader("accountId") accountId: String,
            @PathVariable(name = "transactionId") transactionId: String) {

        //todo: validate input

        tradeLogService.deleteShareTransaction(accountId, transactionId)
    }

    @RequestMapping(value = ["/options", "/options/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createOptionTransaction(
            @RequestHeader("accountId") accountId: String,
            @RequestBody dto: GWCreateOptionJournalDto): GWOptionJournalDto {

        //todo: validate input

        val createModel = CreateOptionJournalConverter.toModel(accountId, dto)
        val createdModel = tradeLogService.createOptionTransaction(accountId, createModel)

        if (createdModel != null) {
            return OptionJournalConverter.toGWDto(createdModel)
        } else {
            throw GatewayApiException(ExceptionCode.CREATE_RESOURCE_FAILED)
        }
    }

    @RequestMapping(value = ["/options/{transactionId}", "/options/{transactionId}/"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteOptionTransaction(
            @RequestHeader("accountId") accountId: String,
            @PathVariable(name = "transactionId") transactionId: String) {

        //todo: validate input

        tradeLogService.deleteOptionTransaction(accountId, transactionId)
    }

    @RequestMapping(value = ["/dividends", "/dividends/"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun createDividendTransaction(
            @RequestHeader("accountId") accountId: String,
            @RequestBody dto: GWCreateDividendJournalDto): GWDividendJournalDto {

        //todo: validate input

        val createModel = CreateDividendJournalConverter.toModel(accountId, dto)
        val createdModel = tradeLogService.createDividendTransaction(accountId, createModel)

        if (createdModel != null) {
            return DividendJournalConverter.toGWDto(createdModel)
        } else {
            throw GatewayApiException(ExceptionCode.CREATE_RESOURCE_FAILED)
        }
    }

    @RequestMapping(value = ["/dividends/{transactionId}", "/dividends/{transactionId}/"], method = [RequestMethod.DELETE])
    @ResponseStatus(HttpStatus.OK)
    fun deleteDividendTransaction(
            @RequestHeader("accountId") accountId: String,
            @PathVariable(name = "transactionId") transactionId: String) {

        //todo: validate input

        tradeLogService.deleteDividendTransaction(accountId, transactionId)
    }

    @RequestMapping(value = ["/settings/{transactionId}", "/settings/{transactionId}/"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    fun updateSettings(
            @RequestHeader("accountId") accountId: String,
            @PathVariable(name = "transactionId") transactionId: String,
            @RequestBody dto: GWTransactionSettingsDto) {

        //todo: validate input
        val model = TransactionSettingsConverter.toModel(dto)
        transactionService.updateTransactionSettings(accountId, model)
    }

}