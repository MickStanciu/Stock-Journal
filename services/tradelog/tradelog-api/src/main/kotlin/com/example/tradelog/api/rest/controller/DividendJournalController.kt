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
class DividendJournalController(
        private val journalFacade: JournalFacade,
        private val journalService: DividendJournalService): DividendJournalRestInterface {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    override fun getAllBySymbol(accountId: UUID, portfolioId: UUID, symbol: String): TLDividendTransactionsResponse {
        if (!RequestValidator.validateGetAllBySymbol(symbol)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val models = journalService.getAllBySymbol(accountId, portfolioId, symbol).orNull() ?: emptyList()
        val dtos = models.map { DividendJournalModelConverter.toDto(it) }

        return TLDividendTransactionsResponse.newBuilder()
                .addAllDividendItems(dtos)
                .build()
    }


    override fun createRecord(accountId: UUID, portfolioId: UUID, dto: TLDividendJournalDto): TLDividendJournalDto {
        if (!RequestValidator.validateCreateDividendRecord(dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        return journalFacade.createDividendRecord(DividendJournalModelConverter.toModel(dto))
                .map { DividendJournalModelConverter.toDto(it) }
                .getOrElse { throw TradeLogException(ExceptionCode.CREATE_DIVIDEND_FAILED) }
    }


    override fun editRecord(accountId: UUID, portfolioId: UUID, transactionId: UUID, dto: TLDividendJournalDto) {
        if (!RequestValidator.validateEditDividendRecord(transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        return journalFacade.editDividendRecord(transactionId, DividendJournalModelConverter.toModel(dto))
                .getOrElse { throw TradeLogException(ExceptionCode.EDIT_DIVIDEND_FAILED) }
    }


    override fun deleteRecord(accountId: UUID, portfolioId: UUID, transactionId: UUID) {
        return journalFacade.deleteDividendRecord(accountId, transactionId)
                .getOrElse { throw TradeLogException(ExceptionCode.DELETE_DIVIDEND_FAILED) }
    }
}
