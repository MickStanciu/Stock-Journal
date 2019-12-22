package com.example.tradelog.api.core.service

import com.example.tradelog.api.core.model.TradeSummaryModel

interface JournalService<T> {

    fun getSummaries(accountId: String): Map<String, TradeSummaryModel>
    fun getAllBySymbol(accountId: String, symbol: String) : T
}