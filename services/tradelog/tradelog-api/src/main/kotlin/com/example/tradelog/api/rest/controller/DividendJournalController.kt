package com.example.tradelog.api.rest.controller

import com.example.tradelog.api.core.facade.JournalFacade
import com.example.tradelog.api.core.service.DividendJournalService
import com.example.tradelog.api.rest.DividendJournalRestInterface
import com.example.tradelog.api.rest.converter.DividendJournalModelConverter
import com.example.tradelog.api.rest.exception.ExceptionCode
import com.example.tradelog.api.rest.exception.TradeLogException
import com.example.tradelog.api.rest.validator.RequestValidator
import com.example.tradelog.api.spec.model.TLDividendJournalDto
import com.example.tradelog.api.spec.model.TLDividendTransactionsResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/v1/dividends"], produces = [DividendJournalController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class DividendJournalController(private val journalFacade: JournalFacade, private val journalService: DividendJournalService): DividendJournalRestInterface {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
        private val LOG = LoggerFactory.getLogger(DividendJournalController::class.java)
    }

    override fun getAllBySymbol(accountId: String, symbol: String, portfolioId: String): TLDividendTransactionsResponse {
        if (!RequestValidator.validateGetAllBySymbol(accountId, symbol)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val models = journalService.getAllBySymbol(accountId, portfolioId, symbol).rightOrNull() ?: emptyList()
        val dtos = models.map { DividendJournalModelConverter.toDto(it) }

        return TLDividendTransactionsResponse.newBuilder()
                .addAllDividendItems(dtos)
                .build()
    }


    override fun createRecord(accountId: String, dto: TLDividendJournalDto): TLDividendJournalDto {
        if (!RequestValidator.validateCreateDividendRecord(accountId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: not sure this it.toString() works
        return journalFacade.createDividendRecord(DividendJournalModelConverter.toModel(dto))
                .mapRight { DividendJournalModelConverter.toDto(it) }
                .rightOrThrow { TradeLogException(ExceptionCode.CREATE_DIVIDEND_FAILED, it.toString()) }
    }


    override fun editRecord(accountId: String, transactionId: String, dto: TLDividendJournalDto) {
        if (!RequestValidator.validateEditDividendRecord(accountId, transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: not sure this it.toString() works
        return journalFacade.editDividendRecord(transactionId, DividendJournalModelConverter.toModel(dto))
                .rightOrThrow { TradeLogException(ExceptionCode.EDIT_DIVIDEND_FAILED, it.toString()) }
    }


    override fun deleteRecord(accountId: String, transactionId: String) {
        if (!RequestValidator.validateDeleteDividendRecord(accountId, transactionId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: HACK
        return journalFacade.deleteDividendRecord(accountId, transactionId)
                .rightOrThrow { TradeLogException(ExceptionCode.DELETE_DIVIDEND_FAILED, it.toString()) }
    }
}
