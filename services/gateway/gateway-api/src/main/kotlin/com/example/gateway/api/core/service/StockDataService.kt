package com.example.gateway.api.core.service

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

    fun getPrice(accountId: String, symbol: String): SharePriceModel? {
        return stockDataGateway.getPrice(accountId, symbol)
    }

    fun updatePrice(symbol: String) {
        val receivedPrice = alphaVantageGateway.getQuoteResponse(symbol)
        if (receivedPrice != null) {
            ampqSender.updatePrice(receivedPrice)
        }
    }
}