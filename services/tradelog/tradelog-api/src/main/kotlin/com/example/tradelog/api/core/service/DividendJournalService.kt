package com.example.tradelog.api.core.service

import com.example.tradelog.api.core.converter.TradeSummaryUtil
import com.example.tradelog.api.core.model.DividendJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.core.model.TransactionSettingsModel
import com.example.tradelog.api.db.repository.DividendJournalRepository
import org.springframework.stereotype.Service

@Service
class DividendJournalService(private val repository: DividendJournalRepository) : JournalService<DividendJournalModel> {

    override fun getSummaries(accountId: String): Map<String, TradeSummaryModel> {
        val modelList = repository.getSummaries(accountId)
        return TradeSummaryUtil.toMap(models = modelList)
    }

    override fun getAllBySymbol(accountId: String, symbol: String): DividendJournalModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateSettings(model: TransactionSettingsModel): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateSettingsBulk(models: List<TransactionSettingsModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createRecord(transactionId: String, model: DividendJournalModel): DividendJournalModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editRecord(transactionId: String, model: DividendJournalModel): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteRecord(transactionId: String, accountId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}