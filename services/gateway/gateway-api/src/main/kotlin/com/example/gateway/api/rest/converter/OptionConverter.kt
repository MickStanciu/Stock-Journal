package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.OptionJournalModel
import com.example.gateway.api.spec.model.OptionJournalDto as GWOptionJournalDto
import com.example.tradelog.api.spec.model.OptionJournalDto as TLOptionJournalDto

class OptionConverter {

    companion object {
        fun toModel(dto: TLOptionJournalDto): OptionJournalModel {
            return OptionJournalModel(
                    transactionId = dto.transactionDetails.id,
                    stockSymbol = dto.transactionDetails.symbol)
        }

        fun toDto(model: OptionJournalModel): GWOptionJournalDto {
            return GWOptionJournalDto.newBuilder()
                    .setTransactionId(model.transactionId)
                    .setStockSymbol(model.stockSymbol)
                    .build()
        }
    }
}
