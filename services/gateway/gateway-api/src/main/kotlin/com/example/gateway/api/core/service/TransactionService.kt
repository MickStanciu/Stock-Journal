package com.example.gateway.api.core.service

import arrow.core.Either
import com.example.common.exception.ApiException
import com.example.gateway.api.core.model.TransactionSettingsModel
import com.example.gateway.api.rest.gateway.TradeLogGateway
import org.springframework.stereotype.Service

@Service
class TransactionService(private val tradeLogGateway: TradeLogGateway) {

    fun updateTransactionSetting(accountId: String, model: TransactionSettingsModel) = tradeLogGateway.updateTransactionSettings(accountId, model)

    fun getActiveSymbols() = tradeLogGateway.getAllActiveSymbols()

    fun updateTransactionSettings(accountId: String, models: List<TransactionSettingsModel>): Either<ApiException, Unit> {
        //NOT THE BEST WAY, USE BATCH NEXT TIME
        var exception: ApiException? = null
        models.forEach {
            val response = updateTransactionSetting(accountId, it)
            if (response.isLeft() && exception == null) exception = response.fold({ ex -> ex }, { null })
        }
        return exception?.let { Either.Left(it) } ?: Either.Right(Unit)
    }

    fun getSummaryMatrix(accountId: String, portfolioId: String, sharesOnly: Boolean) = tradeLogGateway.getSummaryMatrix(accountId, portfolioId, sharesOnly)
}