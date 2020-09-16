package com.example.tradelog.api.rest.controller

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

    override fun getAllBySymbol(accountId: String, symbol: String, portfolioId: String) : TLShareTransactionsResponse {
        if (!RequestValidator.validateGetAllBySymbol(accountId, symbol)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val models = shareService.getAllBySymbol(UUID.fromString(accountId), UUID.fromString(portfolioId), symbol).orNull() ?: emptyList()
        val dtos = models.map { m -> ShareJournalModelConverter.toDto(m) }

        return TLShareTransactionsResponse.newBuilder()
                .addAllShareItems(dtos)
                .build()
    }


    override fun createRecord(accountId: String, dto: TLShareJournalDto): TLShareJournalDto {

        if (!RequestValidator.validateCreateShareRecord(accountId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: not sure this it.toString() works
        return journalFacade.createShareRecord(ShareJournalModelConverter.toModel(dto))
                .map { ShareJournalModelConverter.toDto(it) }
                .rightOrThrow { TradeLogException(ExceptionCode.CREATE_SHARE_FAILED, it.toString()) }
    }


    override fun editRecord(accountId: String, transactionId: String, dto: TLShareJournalDto) {

        if (!RequestValidator.validateEditShareRecord(accountId, transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: not sure this it.toString() works
        return journalFacade.editShareRecord(UUID.fromString(transactionId), ShareJournalModelConverter.toModel(dto))
                .rightOrThrow { TradeLogException(ExceptionCode.EDIT_SHARE_FAILED, it.toString()) }
    }


    override fun deleteRecord(accountId: String, transactionId: String) {

        if (!RequestValidator.validateDeleteShareRecord(accountId, transactionId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        return journalFacade.deleteShareRecord(UUID.fromString(accountId), UUID.fromString(transactionId))
                .rightOrThrow { TradeLogException(ExceptionCode.DELETE_SHARE_FAILED, it.toString()) }
    }
}
