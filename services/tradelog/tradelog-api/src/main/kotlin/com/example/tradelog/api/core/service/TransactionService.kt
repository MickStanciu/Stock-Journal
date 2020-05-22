package com.example.tradelog.api.core.service

import com.example.tradelog.api.core.model.SummaryMatrixModel
import com.example.tradelog.api.core.model.TransactionModel
import com.example.tradelog.api.core.model.TransactionSettingsModel
import com.example.tradelog.api.db.repository.TransactionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TransactionService(private val repository: TransactionRepository) {

    private val log = LoggerFactory.getLogger(TransactionService::class.java)

    fun getAllTradedSymbols(accountId: String, portfolioId: String): List<String> {
        //TODO: HACK
        return repository.getUniqueSymbols(accountId, portfolioId).valueOrNull()!!
    }

    fun getActiveSymbols(): List<String> {
        //TODO: HACK
        return repository.getActiveSymbols().valueOrNull()!!
    }

    fun updateSettings(model: TransactionSettingsModel): Boolean {
        //TODO: HACK
        repository.updateSettings(model)
        return true
    }

    fun updateSettingsBulk(models: List<TransactionSettingsModel>) {
        //todo: find a better way
        //TODO: HACK
        for (model in models) {
            repository.updateSettings(model)
//            if (!repository.updateSettings(model)) {
//                log.error("Failed to update settings for ${model.transactionId}")
//            }
        }
    }

    fun deleteRecord(accountId: String, transactionId: String): Boolean {
        //TODO: HACK
        repository.deleteRecord(accountId, transactionId)
        return true
    }

    fun editRecord(model: TransactionModel): Boolean {
        //TODO: HACK
        repository.updateRecord(model)
        return true
    }

    fun createRecord(model: TransactionModel): String {
        //TODO: HACK
        val id = repository.createRecord(model).valueOrNull()

        val settingsModel = TransactionSettingsModel(transactionId = id!!, preferredPrice = 0.0, groupSelected = true, legClosed = false)
        repository.createSettings(id, settingsModel)
//        if (repository.createSettings(id, settingsModel)) {
//            return id
//        }
        return id
//        return null
    }

    fun deleteSettings(transactionId: String): Boolean {
        //TODO: HACK
        repository.deleteSettings(transactionId)
        return true
    }

    fun getSummaryMatrix(accountId: String, portfolioId: String): List<SummaryMatrixModel> {
        //TODO: HACK
        return repository.getSummaryMatrix(accountId, portfolioId).valueOrNull()!!
    }
}
