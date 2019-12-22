package com.example.tradelog.api.core.service

import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.core.model.TransactionSettingsModel

interface JournalService<T> {

    fun getSummaries(accountId: String): Map<String, TradeSummaryModel>
    fun updateSettings(model: TransactionSettingsModel): Boolean
    fun updateSettingsBulk(models: List<TransactionSettingsModel>)
    fun getAllBySymbol(accountId: String, symbol: String): T
    fun createRecord(transactionId: String, model: T): T
    fun editRecord(transactionId: String, model: T): Boolean
    fun deleteRecord(transactionId: String, accountId: String): Boolean
}