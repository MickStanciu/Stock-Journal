package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.DividendJournalModel
import com.example.tradelog.api.spec.model.DividendJournalDto

class DividendTransactionsResponseConverter {

    companion object {
        fun toModel(dto: DividendJournalDto): DividendJournalModel {
            return DividendJournalModel()
        }
    }

}
