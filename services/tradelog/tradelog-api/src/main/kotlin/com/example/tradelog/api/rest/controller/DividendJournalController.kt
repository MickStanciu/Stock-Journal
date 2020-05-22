package com.example.tradelog.api.rest.controller

import com.example.tradelog.api.core.facade.JournalFacade
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
import java.util.stream.Collectors

@RestController
@RequestMapping(value = ["/api/v1/dividends"], produces = [DividendJournalController.PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class DividendJournalController(private val journalFacade: JournalFacade) : DividendJournalRestInterface {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
        private val LOG = LoggerFactory.getLogger(DividendJournalController::class.java)
    }

    override fun getAllBySymbol(accountId: String, symbol: String, portfolioId: String) : TLDividendTransactionsResponse {
        if (!RequestValidator.validateGetAllBySymbol(accountId, symbol)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val models = journalFacade.getAllDividendTradesBySymbol(accountId, portfolioId, symbol)
        val dtos = models.stream()
                .map { DividendJournalModelConverter.toDto(it) }
                .collect(Collectors.toList())

        return TLDividendTransactionsResponse.newBuilder()
                .addAllDividendItems(dtos)
                .build()
    }


    override fun createRecord(accountId: String, dto: TLDividendJournalDto): TLDividendJournalDto {
        if (!RequestValidator.validateCreateDividendRecord(accountId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val model = journalFacade.createDividendRecord(DividendJournalModelConverter.toModel(dto))

        if (model == null) {
            LOG.error("Could not create for: ${dto.transactionDetails.symbol}")
            throw TradeLogException(ExceptionCode.CREATE_DIVIDEND_FAILED)
        }

        return DividendJournalModelConverter.toDto(model)
    }


    override fun editRecord(accountId: String, transactionId: String, dto: TLDividendJournalDto) {
        if (!RequestValidator.validateEditDividendRecord(accountId, transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: HACK
        journalFacade.editDividendRecord(transactionId, DividendJournalModelConverter.toModel(dto))
//        if (!journalFacade.editDividendRecord(transactionId, DividendJournalModelConverter.toModel(dto))) {
//            LOG.error("Could not edit for: $transactionId")
//            throw TradeLogException(ExceptionCode.EDIT_DIVIDEND_FAILED)
//        }
    }


    override fun deleteRecord(accountId: String, transactionId: String) {
        if (!RequestValidator.validateDeleteDividendRecord(accountId, transactionId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //TODO: HACK
        journalFacade.deleteDividendRecord(accountId, transactionId)
//        if (!journalFacade.deleteDividendRecord(accountId, transactionId)) {
//            LOG.error("Could not delete for: $transactionId")
//            throw TradeLogException(ExceptionCode.DELETE_DIVIDEND_FAILED)
//        }
    }
}
