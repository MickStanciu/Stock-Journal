package com.example.tradelog.api.rest.converter

import com.example.tradelog.api.core.model.DividendJournalModel
import com.example.tradelog.api.spec.model.DividendJournalDto

class DividendJournalModelConverter {

    companion object {
        fun toModel(dto: DividendJournalDto): DividendJournalModel {
            return DividendJournalModel(
                    transactionDetails = TransactionModelConverter.toModel(dto.transactionDetails),
                    dividend = dto.dividend,
                    quantity = dto.quantity
            );
        }

        fun toDto(model: DividendJournalModel): DividendJournalDto {
            return DividendJournalDto.newBuilder()
                    .setTransactionDetails(TransactionModelConverter.toDto(model.transactionDetails))
                    .setDividend(model.dividend)
                    .setQuantity(model.quantity)
                    .build()
        }
    }
}
