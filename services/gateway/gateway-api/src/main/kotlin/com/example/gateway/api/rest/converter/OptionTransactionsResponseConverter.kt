package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.OptionJournalModel
import com.example.tradelog.api.spec.model.OptionJournalDto

class OptionTransactionsResponseConverter {

    companion object {
        fun toModel(dto: OptionJournalDto): OptionJournalModel {
            return OptionJournalModel()
        }
    }
}
