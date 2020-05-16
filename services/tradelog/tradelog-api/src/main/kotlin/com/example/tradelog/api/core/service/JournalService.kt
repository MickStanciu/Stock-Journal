package com.example.tradelog.api.core.service

import com.example.common.error.DataAccessError
import com.example.common.types.Either
import com.example.tradelog.api.core.model.TradeSummaryModel

interface JournalService<E, V> {

    fun getSummaries(accountId: String): Map<String, TradeSummaryModel>
    fun getAllBySymbol(accountId: String, portfolioId: String, symbol: String): List<V>
    fun getById(accountId: String, transactionId: String): Either<E, V>
    fun createRecord(transactionId: String, model: V): V?
    fun editRecord(model: V): Boolean
    fun deleteRecord(transactionId: String): Boolean

    sealed class TradeLogBusinessError {
        class RecordNotFound(val message: String): TradeLogBusinessError()
        class UnknownError(val message: String = "Unknown error"): TradeLogBusinessError()
    }

    fun toBusinessError(e: DataAccessError): TradeLogBusinessError = when(e) {
        is DataAccessError.RecordNotFound -> TradeLogBusinessError.RecordNotFound(e.message)
        else -> TradeLogBusinessError.UnknownError()
    }
}
