package com.example.gateway.api.core.service

import com.example.common.converter.TimeConverter
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

    fun getPrice(symbol: String): SharePriceModel? {
        return stockDataGateway.getPrice(symbol)
    }

    fun updatePrice(model: SharePriceModel) {
        val receivedPrice = alphaVantageGateway.getQuoteResponse(model.symbol)
        if (receivedPrice != null) {
            ampqSender.updatePrice(receivedPrice)
        } else {
            var active = model.active
            if (model.lastFailedOn != null) {
                active = false
            }
            ampqSender.updatePrice(model.copy(
                    lastFailedOn = TimeConverter.getOffsetDateTimeNow(),
                    active = active))
        }
    }

    fun getSymbolsForUpdate(): List<SharePriceModel> {
        return stockDataGateway.getSymbolsForUpdate()
    }

    fun updateSymbols(symbols: List<String>) {
        stockDataGateway.updateSymbols(symbols)
    }
}
