package com.example.tradelog.api.core.service

import com.example.tradelog.api.core.model.TransactionModel
import com.example.tradelog.api.core.model.TransactionSettingsModel
import com.example.tradelog.api.db.repository.TransactionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TransactionService(private val repository: TransactionRepository) {

    private val log = LoggerFactory.getLogger(TransactionService::class.java)

    fun getAllTradedSymbols(accountId: String): List<String> {
        return repository.getUniqueSymbols(accountId)
    }

    fun updateSettings(model: TransactionSettingsModel): Boolean {
        return repository.updateSettings(model)
    }

    fun updateSettingsBulk(models: List<TransactionSettingsModel>) {
        //todo: find a better way
        for (model in models) {
            if (!repository.updateSettings(model)) {
                log.error("Failed to update settings for ${model.transactionId}")
            }
        }
    }

    fun deleteRecord(accountId: String, transactionId: String): Boolean {
        return repository.deleteRecord(accountId, transactionId)
    }

    fun editRecord(model: TransactionModel): Boolean {
        return repository.updateRecord(model)
    }

    fun createRecord(model: TransactionModel): String? {
        val id = repository.createRecord(model) ?: return null

        val settingsModel = TransactionSettingsModel(transactionId = id, preferredPrice = 0.0, groupSelected = true, legClosed = false)
        if (repository.createSettings(id, settingsModel)) {
            return id
        }
        return null
    }

    fun deleteSettings(transactionId: String): Boolean {
        return repository.deleteSettings(transactionId)
    }

}
