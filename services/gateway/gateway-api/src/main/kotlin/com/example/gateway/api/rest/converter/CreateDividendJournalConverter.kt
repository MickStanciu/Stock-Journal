package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.DividendJournalModel
import com.example.gateway.api.core.model.SupportMetadata
import com.example.gateway.api.spec.model.GWCreateDividendJournalDto

class CreateDividendJournalConverter {

    companion object {
        fun toModel(supportMetadata: SupportMetadata, dto: GWCreateDividendJournalDto): DividendJournalModel {
            return DividendJournalModel(
                    transactionId = null,
                    accountId = supportMetadata.accountId,
                    portfolioId = supportMetadata.portfolioId,
                    date = TimeConverter.toOffsetDateTime(dto.date),
                    symbol = dto.symbol,
                    quantity = dto.quantity,
                    dividend = dto.dividend,
                    legClosed = false,
                    groupSelected = false)
        }
    }
}