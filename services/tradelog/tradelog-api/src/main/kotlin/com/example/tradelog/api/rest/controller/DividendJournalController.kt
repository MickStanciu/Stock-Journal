package com.example.tradelog.api.rest.controller

import arrow.core.getOrElse
import com.example.tradelog.api.core.facade.JournalFacade
import com.example.tradelog.api.core.service.DividendJournalService
import com.example.tradelog.api.rest.DividendJournalRestInterface
import com.example.tradelog.api.rest.converter.DividendJournalModelConverter
import com.example.tradelog.api.rest.exception.ExceptionCode
import com.example.tradelog.api.rest.exception.TradeLogException
import com.example.tradelog.api.rest.validator.RequestValidator
import com.example.tradelog.api.spec.model.TLDividendJournalDto
import com.example.tradelog.api.spec.model.TLDividendTransactionsResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(value = ["/api/v1/dividends"],
        produces = [DividendJournalController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE],
        consumes = [DividendJournalController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class DividendJournalController(private val journalFacade: JournalFacade, private val journalService: DividendJournalService): DividendJournalRestInterface {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    override fun getAllBySymbol(accountId: String, symbol: String, portfolioId: String): TLDividendTransactionsResponse {
        if (!RequestValidator.validateGetAllBySymbol(accountId, symbol)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val models = journalService.getAllBySymbol(UUID.fromString(accountId), UUID.fromString(portfolioId), symbol).orNull() ?: emptyList()
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
                .map { DividendJournalModelConverter.toDto(it) }
                .getOrElse { throw TradeLogException(ExceptionCode.CREATE_DIVIDEND_FAILED) }
    }


    override fun editRecord(accountId: String, transactionId: String, dto: TLDividendJournalDto) {
        if (!RequestValidator.validateEditDividendRecord(accountId, transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: not sure this it.toString() works
        return journalFacade.editDividendRecord(UUID.fromString(transactionId), DividendJournalModelConverter.toModel(dto))
                .getOrElse { throw TradeLogException(ExceptionCode.EDIT_DIVIDEND_FAILED) }
    }


    override fun deleteRecord(accountId: String, transactionId: String) {
        if (!RequestValidator.validateDeleteDividendRecord(accountId, transactionId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        return journalFacade.deleteDividendRecord(UUID.fromString(accountId), UUID.fromString(transactionId))
                .getOrElse { throw TradeLogException(ExceptionCode.DELETE_DIVIDEND_FAILED) }
    }
}
