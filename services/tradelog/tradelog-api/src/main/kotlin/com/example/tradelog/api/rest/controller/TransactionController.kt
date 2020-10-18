package com.example.tradelog.api.rest.controller

import arrow.core.getOrElse
import com.example.tradelog.api.core.facade.JournalFacade
import com.example.tradelog.api.core.service.TransactionService
import com.example.tradelog.api.rest.TransactionRestInterface
import com.example.tradelog.api.rest.controller.TransactionController.Companion.PROTOBUF_MEDIA_TYPE_VALUE
import com.example.tradelog.api.rest.converter.SummaryMatrixConverter
import com.example.tradelog.api.rest.converter.TradeSummaryConverter
import com.example.tradelog.api.rest.converter.TransactionSettingsModelConverter
import com.example.tradelog.api.rest.exception.ExceptionCode
import com.example.tradelog.api.rest.exception.TradeLogException
import com.example.tradelog.api.rest.validator.RequestValidator
import com.example.tradelog.api.spec.model.TLActiveSymbolsResponse
import com.example.tradelog.api.spec.model.TLSummaryMatrixResponse
import com.example.tradelog.api.spec.model.TLTradeSummaryResponse
import com.example.tradelog.api.spec.model.TLTransactionSettingsDto
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(value = ["/api/v1/transactions"],
        produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE],
        consumes = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE]
)
class TransactionController(
        private val journalFacade: JournalFacade,
        private val transactionService: TransactionService): TransactionRestInterface {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    /*
        Return the unique symbols that have been traded during last year
    */
    override fun getAllActiveSymbols(): TLActiveSymbolsResponse {
        val symbols = transactionService.getActiveSymbols().orNull() ?: emptyList()

        return TLActiveSymbolsResponse.newBuilder()
                .addAllSymbols(symbols)
                .build()
    }


    override fun getSummary(accountId: UUID, portfolioId: UUID): TLTradeSummaryResponse {
        val summaryList = journalFacade.getSummary(accountId)
                .getOrElse { throw TradeLogException(ExceptionCode.UNKNOWN) }

        return TradeSummaryConverter.toTradeSummaryResponse(summaryList)
    }

    override fun getSummaryMatrix(accountId: UUID, portfolioId: UUID, sharesOnly: Boolean): TLSummaryMatrixResponse {
        val type = if (sharesOnly) JournalFacade.SummaryMatrixType.SHARES else JournalFacade.SummaryMatrixType.OPTIONS_AND_DIVIDENDS
        val summaryList = journalFacade.getSummaryMatrix(accountId, portfolioId, type)
                .getOrElse { throw TradeLogException(ExceptionCode.UNKNOWN) }

        return SummaryMatrixConverter.toSummaryMatrixResponse(summaryList)
    }


    override fun updateSettings(accountId: UUID, portfolioId: UUID, transactionId: UUID, dto: TLTransactionSettingsDto) {
        if (!RequestValidator.validateUpdateSettings(transactionId, dto)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        //Todo: enforce account id
        transactionService.updateSettings(TransactionSettingsModelConverter.toModel(dto))
                .getOrElse { throw TradeLogException(ExceptionCode.UPDATE_TRANSACTION_OPTIONS_FAILED) }
    }
}
