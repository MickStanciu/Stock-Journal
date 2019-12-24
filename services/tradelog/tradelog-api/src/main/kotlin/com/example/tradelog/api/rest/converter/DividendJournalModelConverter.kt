package com.example.tradelog.api.rest.converter

import com.example.tradelog.api.core.model.DividendJournalModel
import com.example.tradelog.api.spec.model.DividendJournalDto
import com.example.tradelog.api.spec.model.DividendTransactionsResponse

class DividendJournalModelConverter {

    companion object {
        fun toDividendTransactionsResponse(models: List<DividendJournalModel>): DividendTransactionsResponse {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        fun toModel(dto: DividendJournalDto): DividendJournalModel {
            return DividendJournalModel(
                    transactionDetails = TransactionModelConverter.toModel(dto.transactionDetails),
                    dividend = dto.dividend,
                    quantity = dto.quantity
            );
        }

        fun toDto(model: DividendJournalModel): DividendJournalDto {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}
