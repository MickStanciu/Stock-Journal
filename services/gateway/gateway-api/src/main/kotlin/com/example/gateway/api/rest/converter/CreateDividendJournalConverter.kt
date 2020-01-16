package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.DividendJournalModel
import com.example.gateway.api.spec.model.CreateDividendJournalDto as GWCreateDividendJournalDto

class CreateDividendJournalConverter {

    companion object {
        fun toModel(accountId: String, dto: GWCreateDividendJournalDto): DividendJournalModel {
            return DividendJournalModel(
                    transactionId = "",
                    accountId = accountId,
                    date = TimeConverter.toOffsetDateTime.apply(dto.date),
                    symbol = dto.symbol,
                    quantity = dto.quantity,
                    dividend = dto.dividend,
                    legClosed = false,
                    groupSelected = false)
        }
    }
}