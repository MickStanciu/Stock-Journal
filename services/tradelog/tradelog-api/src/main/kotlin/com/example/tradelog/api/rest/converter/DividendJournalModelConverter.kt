package com.example.tradelog.api.rest.converter

import com.example.tradelog.api.core.model.DividendJournalModel
import com.example.tradelog.api.spec.model.TLDividendJournalDto

class DividendJournalModelConverter {

    companion object {
        fun toModel(dto: TLDividendJournalDto): DividendJournalModel {
            return DividendJournalModel(
                    transactionDetails = TransactionModelConverter.toModel(dto.transactionDetails),
                    dividend = dto.dividend,
                    quantity = dto.quantity
            );
        }

        fun toDto(model: DividendJournalModel): TLDividendJournalDto {
            return TLDividendJournalDto.newBuilder()
                    .setTransactionDetails(TransactionModelConverter.toDto(model.transactionDetails))
                    .setDividend(model.dividend)
                    .setQuantity(model.quantity)
                    .build()
        }
    }
}
