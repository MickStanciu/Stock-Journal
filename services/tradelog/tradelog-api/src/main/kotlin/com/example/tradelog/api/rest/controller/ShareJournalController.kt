package com.example.tradelog.api.rest.controller

import arrow.core.getOrElse
import com.example.tradelog.api.core.facade.JournalFacade
import com.example.tradelog.api.core.service.ShareJournalService
import com.example.tradelog.api.rest.ShareJournalRestInterface
import com.example.tradelog.api.rest.converter.ShareJournalModelConverter
import com.example.tradelog.api.rest.exception.ExceptionCode
import com.example.tradelog.api.rest.exception.TradeLogException
import com.example.tradelog.api.rest.validator.RequestValidator
import com.example.tradelog.api.spec.model.TLShareJournalDto
import com.example.tradelog.api.spec.model.TLShareTransactionsResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(value = ["/api/v1/shares"],
        produces = [ShareJournalController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE],
        consumes = [ShareJournalController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE]
)
class ShareJournalController(private val journalFacade: JournalFacade, private val shareService: ShareJournalService): ShareJournalRestInterface {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    override fun getAllBySymbol(accountId: UUID, portfolioId: UUID, symbol: String) : TLShareTransactionsResponse {
        if (!RequestValidator.validateGetAllBySymbol(symbol)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val models = shareService.getAllBySymbol(accountId, portfolioId, symbol).orNull() ?: emptyList()
        val dtos = models.map { m -> ShareJournalModelConverter.toDto(m) }

        return TLShareTransactionsResponse.newBuilder()
                .addAllShareItems(dtos)
                .build()
    }


    override fun createRecord(accountId: UUID, portfolioId: UUID, dto: TLShareJournalDto): TLShareJournalDto {

        if (!RequestValidator.validateCreateShareRecord(dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        return journalFacade.createShareRecord(ShareJournalModelConverter.toModel(dto))
                .map { ShareJournalModelConverter.toDto(it) }
                .getOrElse { throw TradeLogException(ExceptionCode.CREATE_SHARE_FAILED) }
    }


    override fun editRecord(accountId: UUID, portfolioId: UUID, transactionId: UUID, dto: TLShareJournalDto) {

        if (!RequestValidator.validateEditShareRecord(transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        return journalFacade.editShareRecord(transactionId, ShareJournalModelConverter.toModel(dto))
                .getOrElse { throw TradeLogException(ExceptionCode.EDIT_SHARE_FAILED) }
    }


    override fun deleteRecord(accountId: UUID, portfolioId: UUID, transactionId: UUID) {
        return journalFacade.deleteShareRecord(accountId, transactionId)
                .getOrElse { throw TradeLogException(ExceptionCode.DELETE_SHARE_FAILED) }
    }
}
