package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.DividendJournalModel
import com.example.gateway.api.spec.model.DividendJournalDto as GWDividendJournalDto
import com.example.tradelog.api.spec.model.DividendJournalDto as TLDividendJournalDto

class DividendJournalConverter {

    companion object {
        fun toModel(dto: TLDividendJournalDto): DividendJournalModel {
            return DividendJournalModel(
                    transactionId = dto.transactionDetails.id,
                    symbol = dto.transactionDetails.symbol,
                    date = TimeConverter.toOffsetDateTime.apply(dto.transactionDetails.date),
                    dividend = dto.dividend,
                    quantity = dto.quantity,
                    groupSelected = dto.transactionDetails.settings.groupSelected,
                    legClosed = dto.transactionDetails.settings.legClosed
            )
        }

        fun toDto(model: DividendJournalModel): GWDividendJournalDto {
            return GWDividendJournalDto.newBuilder()
                    .setTransactionId(model.transactionId)
                    .setSymbol(model.symbol)
                    .setDate(model.date.toString())
                    .setDividend(model.dividend)
                    .setQuantity(model.quantity)
                    .setGroupSelected(model.groupSelected)
                    .setLegClosed(model.legClosed)
                    .build()
        }
    }

}
