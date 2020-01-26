package com.example.gateway.api.core.service

import com.example.gateway.api.core.model.ShareDataModel
import com.example.gateway.api.rest.gateway.StockDataGateway
import org.springframework.stereotype.Service

@Service
class StockDataService(
        private val stockDataGateway: StockDataGateway) {

    fun getPrice(accountId: String, symbol: String): ShareDataModel? {
        return stockDataGateway.getPrice(accountId, symbol)
    }
}