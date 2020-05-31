package com.example.tradelog.api.rest.controller

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

        val models = optionService.getAllBySymbol(accountId, portfolioId, symbol).rightOrNull() ?: emptyList()
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
                .mapRight { OptionJournalModelConverter.toDto(it) }
                .rightOrThrow { TradeLogException(ExceptionCode.CREATE_OPTION_FAILED, it.toString()) }
    }

    override fun editRecord(accountId: String, transactionId: String, dto: TLOptionJournalDto) {
        if (!RequestValidator.validateEditOptionRecord(accountId, transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: not sure this it.toString() works
        return journalFacade.editOptionRecord(transactionId, OptionJournalModelConverter.toModel(dto))
                .rightOrThrow { TradeLogException(ExceptionCode.EDIT_OPTION_FAILED, it.toString()) }
    }

    override fun deleteRecord(accountId: String, transactionId: String) {
        if (!RequestValidator.validateDeleteOptionRecord(accountId, transactionId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        return journalFacade.deleteOptionRecord(accountId, transactionId)
                .rightOrThrow { TradeLogException(ExceptionCode.DELETE_OPTION_FAILED, it.toString()) }
    }

}
