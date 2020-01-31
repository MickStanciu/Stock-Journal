package com.example.gateway.api.core.service

import com.example.gateway.api.core.model.TransactionSettingsModel
import com.example.gateway.api.rest.gateway.TradeLogGateway
import org.springframework.stereotype.Service

@Service
class TransactionService(private val tradeLogGateway: TradeLogGateway) {

    fun updateTransactionSettings(accountId: String, model: TransactionSettingsModel) {
        tradeLogGateway.updateTransactionSettings(accountId, model)
    }

    fun getActiveSymbols(): List<String> {
        return tradeLogGateway.getAllActiveSymbols()
    }
}