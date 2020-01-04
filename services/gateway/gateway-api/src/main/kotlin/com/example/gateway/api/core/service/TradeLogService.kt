package com.example.gateway.api.core.service

import com.example.gateway.api.rest.gateway.TradeLogGateway
import org.springframework.stereotype.Service

@Service
class TradeLogService(private val tradeLogGateway: TradeLogGateway) {

    fun getAllTradedSymbols(accountId: String): List<String> {
        return tradeLogGateway.getAllTradedSymbols(accountId)
    }
}