package com.example.gateway.api.core.service

import arrow.core.Either
import com.example.common.converter.TimeConverter
import com.example.common.exception.ApiException
import com.example.gateway.api.amqp.AmqpSender
import com.example.gateway.api.core.model.SharePriceModel
import com.example.gateway.api.rest.gateway.AlphaVantageGateway
import com.example.gateway.api.rest.gateway.StockDataGateway
import org.springframework.stereotype.Service

@Service
class StockDataService(
        private val stockDataGateway: StockDataGateway,
        private val alphaVantageGateway: AlphaVantageGateway,
        private val ampqSender: AmqpSender) {

    fun getPrice(symbol: String) = stockDataGateway.getPrice(symbol)

    fun updatePrice(model: SharePriceModel): Either<ApiException, Unit> {
        val now = TimeConverter.getOffsetDateTimeNow()

        return alphaVantageGateway
                .getQuoteResponse(model.symbol)
                .bimap(
                        {
                            var active = model.active
                            if (model.lastFailedOn != null) {
                                active = false
                            }

                            model.lastFailedOn = now
                            model.lastUpdatedOn = now
                            model.active = active
                            ampqSender.updatePrice(model)
                            it
                        },
                        {
                            it.lastUpdatedOn = now
                            ampqSender.updatePrice(it)
                        }
                )

    }

    fun getSymbolsForUpdate() = stockDataGateway.getSymbolsForUpdate()

    fun updateSymbols(symbols: List<String>) = stockDataGateway.updateSymbols(symbols)
}
