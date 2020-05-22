package com.example.tradelog.api.rest.controller

import com.example.tradelog.api.core.facade.JournalFacade
import com.example.tradelog.api.rest.OptionJournalRestInterface
import com.example.tradelog.api.rest.converter.OptionJournalModelConverter
import com.example.tradelog.api.rest.exception.ExceptionCode
import com.example.tradelog.api.rest.exception.TradeLogException
import com.example.tradelog.api.rest.validator.RequestValidator
import com.example.tradelog.api.spec.model.TLOptionJournalDto
import com.example.tradelog.api.spec.model.TLOptionTransactionsResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping(value = ["/api/v1/options"],
        produces = [OptionJournalController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE],
        consumes = [ShareJournalController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class OptionJournalController(private val journalFacade: JournalFacade) : OptionJournalRestInterface {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
        private val LOG = LoggerFactory.getLogger(OptionJournalController::class.java)
    }

    override fun getAllBySymbol(accountId: String, symbol: String, portfolioId: String) : TLOptionTransactionsResponse {
        if (!RequestValidator.validateGetAllBySymbol(accountId, symbol)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val models = journalFacade.getAllOptionTradesBySymbol(accountId, portfolioId, symbol)
        val dtos = models.stream()
                .map { m -> OptionJournalModelConverter.toDto(m) }
                .collect(Collectors.toList())

        return TLOptionTransactionsResponse.newBuilder()
                .addAllOptionItems(dtos)
                .build()
    }

    override fun createRecord(accountId: String, dto: TLOptionJournalDto): TLOptionJournalDto {
        if (!RequestValidator.validateCreateOptionRecord(accountId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val model = journalFacade.createOptionRecord(OptionJournalModelConverter.toModel(dto))

        if (model == null) {
            LOG.error("Could not create for: ${dto.transactionDetails.symbol}")
            throw TradeLogException(ExceptionCode.CREATE_OPTION_FAILED)
        }

        return OptionJournalModelConverter.toDto(model)
    }

    override fun editRecord(accountId: String, transactionId: String, dto: TLOptionJournalDto) {
        if (!RequestValidator.validateEditOptionRecord(accountId, transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: HACK
        journalFacade.editOptionRecord(transactionId, OptionJournalModelConverter.toModel(dto))
//        if (!journalFacade.editOptionRecord(transactionId, OptionJournalModelConverter.toModel(dto))) {
//            LOG.error("Could not edit for: $transactionId")
//            throw TradeLogException(ExceptionCode.EDIT_SHARE_FAILED)
//        }
    }

    override fun deleteRecord(accountId: String, transactionId: String) {
        if (!RequestValidator.validateDeleteOptionRecord(accountId, transactionId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: HACK
        journalFacade.deleteOptionRecord(accountId, transactionId)
//        if (!journalFacade.deleteOptionRecord(accountId, transactionId)) {
//            LOG.error("Could not delete for: $transactionId")
//            throw TradeLogException(ExceptionCode.DELETE_OPTION_FAILED)
//        }
    }

}
