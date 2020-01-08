package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.DividendJournalModel
import com.example.gateway.api.spec.model.GWDividendJournalDto
import com.example.tradelog.api.spec.model.DividendJournalDto as TLDividendJournalDto

class DividendTransactionsResponseConverter {

    companion object {
        fun toModel(dto: TLDividendJournalDto): DividendJournalModel {
            return DividendJournalModel(
                    transactionId = dto.transactionDetails.id,
                    symbol = dto.transactionDetails.symbol
            )
        }

        fun toDto(model: DividendJournalModel): GWDividendJournalDto {
            return GWDividendJournalDto.newBuilder()
                    .setTransactionId(model.transactionId)
                    .setSymbol(model.symbol)
                    .build()
        }
    }

}
