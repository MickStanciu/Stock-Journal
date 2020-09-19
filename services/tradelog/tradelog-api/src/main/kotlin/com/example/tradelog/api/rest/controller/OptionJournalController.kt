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

    override fun getAllBySymbol(accountId: String, symbol: String, portfolioId: String): TLOptionTransactionsResponse {
        if (!RequestValidator.validateGetAllBySymbol(accountId, symbol)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val models = optionService.getAllBySymbol(UUID.fromString(accountId), UUID.fromString(portfolioId), symbol).orNull() ?: emptyList()
        val dtos = models.map { m -> OptionJournalModelConverter.toDto(m) }

        return TLOptionTransactionsResponse.newBuilder()
                .addAllOptionItems(dtos)
                .build()
    }

    override fun createRecord(accountId: String, dto: TLOptionJournalDto): TLOptionJournalDto {
        if (!RequestValidator.validateCreateOptionRecord(accountId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: not sure this it.toString() works
        return journalFacade.createOptionRecord(OptionJournalModelConverter.toModel(dto))
                .map { OptionJournalModelConverter.toDto(it) }
                .getOrElse { throw TradeLogException(ExceptionCode.CREATE_OPTION_FAILED) }
    }

    override fun editRecord(accountId: String, transactionId: String, dto: TLOptionJournalDto) {
        if (!RequestValidator.validateEditOptionRecord(accountId, transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: not sure this it.toString() works
        return journalFacade.editOptionRecord(UUID.fromString(transactionId), OptionJournalModelConverter.toModel(dto))
                .getOrElse { throw TradeLogException(ExceptionCode.EDIT_OPTION_FAILED) }
    }

    override fun deleteRecord(accountId: String, transactionId: String) {
        if (!RequestValidator.validateDeleteOptionRecord(accountId, transactionId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        return journalFacade.deleteOptionRecord(UUID.fromString(accountId), UUID.fromString(transactionId))
                .getOrElse { throw TradeLogException(ExceptionCode.DELETE_OPTION_FAILED) }
    }

}
