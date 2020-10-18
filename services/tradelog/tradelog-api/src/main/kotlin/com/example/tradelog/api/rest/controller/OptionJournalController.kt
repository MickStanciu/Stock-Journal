package com.example.tradelog.api.rest.controller

import arrow.core.getOrElse
import com.example.tradelog.api.core.facade.JournalFacade
import com.example.tradelog.api.core.service.OptionJournalService
import com.example.tradelog.api.rest.OptionJournalRestInterface
import com.example.tradelog.api.rest.converter.OptionJournalModelConverter
import com.example.tradelog.api.rest.exception.ExceptionCode
import com.example.tradelog.api.rest.exception.TradeLogException
import com.example.tradelog.api.rest.validator.RequestValidator
import com.example.tradelog.api.spec.model.TLOptionJournalDto
import com.example.tradelog.api.spec.model.TLOptionTransactionsResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(value = ["/api/v1/options"],
        produces = [OptionJournalController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE],
        consumes = [OptionJournalController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE]
)
class OptionJournalController(private val journalFacade: JournalFacade, private val optionService: OptionJournalService): OptionJournalRestInterface {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    override fun getAllBySymbol(accountId: UUID, portfolioId: UUID, symbol: String): TLOptionTransactionsResponse {
        if (!RequestValidator.validateGetAllBySymbol(symbol)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val models = optionService.getAllBySymbol(accountId, portfolioId, symbol).orNull() ?: emptyList()
        val dtos = models.map { m -> OptionJournalModelConverter.toDto(m) }

        return TLOptionTransactionsResponse.newBuilder()
                .addAllOptionItems(dtos)
                .build()
    }

    override fun createRecord(accountId: UUID, portfolioId: UUID, dto: TLOptionJournalDto): TLOptionJournalDto {
        if (!RequestValidator.validateCreateOptionRecord(dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        return journalFacade.createOptionRecord(OptionJournalModelConverter.toModel(dto))
                .map { OptionJournalModelConverter.toDto(it) }
                .getOrElse { throw TradeLogException(ExceptionCode.CREATE_OPTION_FAILED) }
    }

    override fun editRecord(accountId: UUID, portfolioId: UUID, transactionId: UUID, dto: TLOptionJournalDto) {
        if (!RequestValidator.validateEditOptionRecord(transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        return journalFacade.editOptionRecord(transactionId, OptionJournalModelConverter.toModel(dto))
                .getOrElse { throw TradeLogException(ExceptionCode.EDIT_OPTION_FAILED) }
    }

    override fun deleteRecord(accountId: UUID, portfolioId: UUID, transactionId: UUID) {
        return journalFacade.deleteOptionRecord(accountId, transactionId)
                .getOrElse { throw TradeLogException(ExceptionCode.DELETE_OPTION_FAILED) }
    }

}
