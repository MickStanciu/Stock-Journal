package com.example.tradelog.api.rest.controller

import com.example.tradelog.api.core.facade.JournalFacade
import com.example.tradelog.api.rest.PortfolioRestInterface
import com.example.tradelog.api.rest.controller.PortfolioController.Companion.PROTOBUF_MEDIA_TYPE_VALUE
import com.example.tradelog.api.rest.converter.PortfolioModelConverter
import com.example.tradelog.api.rest.exception.ExceptionCode
import com.example.tradelog.api.rest.exception.TradeLogException
import com.example.tradelog.api.rest.validator.RequestValidator
import com.example.tradelog.api.spec.model.TLPortfolioResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping(value = ["/api/v1/portfolios"], produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class PortfolioController(private val journalFacade: JournalFacade) : PortfolioRestInterface {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    override fun getPortfolios(accountId: String): TLPortfolioResponse {

        if (!RequestValidator.validateGetPortfolios(accountId)) {
            throw TradeLogException(ExceptionCode.BAD_REQUEST)
        }

        val portfolioModels = journalFacade.getPortfolios(accountId)
        val portfolioDtos = portfolioModels.stream()
                .map { PortfolioModelConverter.toDto(it) }
                .collect(Collectors.toList())

        return TLPortfolioResponse.newBuilder()
                .addAllItems(portfolioDtos)
                .build()
    }
}