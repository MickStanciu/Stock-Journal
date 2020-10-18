package com.example.tradelog.api.rest.controller

import com.example.tradelog.api.core.service.PortfolioService
import com.example.tradelog.api.rest.PortfolioRestInterface
import com.example.tradelog.api.rest.controller.PortfolioController.Companion.PROTOBUF_MEDIA_TYPE_VALUE
import com.example.tradelog.api.rest.converter.PortfolioModelConverter
import com.example.tradelog.api.rest.exception.ExceptionCode
import com.example.tradelog.api.rest.exception.TradeLogException
import com.example.tradelog.api.spec.model.TLPortfolioDto
import com.example.tradelog.api.spec.model.TLPortfolioResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(value = ["/api/v1/portfolios"],
        produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE],
        consumes = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE]
)
class PortfolioController(private val portfolioService: PortfolioService): PortfolioRestInterface {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    override fun getPortfolios(accountId: UUID): TLPortfolioResponse {
        val portfolioModels = portfolioService.getPortfolios(accountId).orNull() ?: emptyList()
        val portfolioDtos = portfolioModels.map { PortfolioModelConverter.toDto(it) }

        return TLPortfolioResponse.newBuilder()
                .addAllItems(portfolioDtos)
                .build()
    }

    override fun getDefaultPortfolio(accountId: UUID): TLPortfolioDto {
        val model = portfolioService.getDefaultPortfolio(accountId).orNull()
                ?: throw TradeLogException(ExceptionCode.NO_DEFAULT_PORTFOLIO)

        return PortfolioModelConverter.toDto(model = model)
    }
}