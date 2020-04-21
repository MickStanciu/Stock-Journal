package com.example.tradelog.api.rest.controller

import com.example.tradelog.api.core.facade.JournalFacade
import com.example.tradelog.api.rest.ShareJournalRestInterface
import com.example.tradelog.api.rest.converter.ShareJournalModelConverter
import com.example.tradelog.api.rest.exception.ExceptionCode
import com.example.tradelog.api.rest.exception.TradeLogException
import com.example.tradelog.api.rest.validator.RequestValidator
import com.example.tradelog.api.spec.model.TLShareJournalDto
import com.example.tradelog.api.spec.model.TLShareTransactionsResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping(value = ["/api/v1/shares"],
        produces = [ShareJournalController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE],
        consumes = [ShareJournalController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class ShareJournalController(private val journalFacade: JournalFacade) : ShareJournalRestInterface {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
        private val LOG = LoggerFactory.getLogger(ShareJournalController::class.java)
    }

    override fun getAllBySymbol(accountId: String, symbol: String, portfolioId: String) : TLShareTransactionsResponse {
        if (!RequestValidator.validateGetAllBySymbol(accountId, symbol)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val models = journalFacade.getAllShareTradesBySymbol(accountId, portfolioId, symbol)
        val dtos = models.stream()
                .map { ShareJournalModelConverter.toDto(it) }
                .collect(Collectors.toList())

        return TLShareTransactionsResponse.newBuilder()
                .addAllShareItems(dtos)
                .build()
    }


    override fun createRecord(accountId: String, dto: TLShareJournalDto): TLShareJournalDto {

        if (!RequestValidator.validateCreateShareRecord(accountId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val model = journalFacade.createShareRecord(ShareJournalModelConverter.toModel(dto))

        if (model == null) {
            LOG.error("Could not create for: ${dto.transactionDetails.symbol}")
            throw TradeLogException(ExceptionCode.CREATE_SHARE_FAILED)
        }

        return ShareJournalModelConverter.toDto(model = model)
    }


    override fun editRecord(accountId: String, transactionId: String, dto: TLShareJournalDto) {

        if (!RequestValidator.validateEditShareRecord(accountId, transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        if (!journalFacade.editShareRecord(transactionId, ShareJournalModelConverter.toModel(dto))) {
            LOG.error("Could not edit for: $transactionId")
            throw TradeLogException(ExceptionCode.EDIT_SHARE_FAILED)
        }
    }


    override fun deleteRecord(accountId: String, transactionId: String) {

        if (!RequestValidator.validateDeleteShareRecord(accountId, transactionId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        if (!journalFacade.deleteShareRecord(accountId, transactionId)) {
            LOG.error("Could not delete for: $transactionId")
            throw TradeLogException(ExceptionCode.DELETE_SHARE_FAILED)
        }
    }
}
