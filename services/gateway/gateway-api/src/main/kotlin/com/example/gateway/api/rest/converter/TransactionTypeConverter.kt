package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.TransactionType
import com.example.gateway.api.spec.model.ShareJournalDto as GWShareJournalDto
import com.example.tradelog.api.spec.model.TransactionDto as TLTransactionDto

class TransactionTypeConverter {

    companion object {
        fun toModel(dto: TLTransactionDto.TransactionType): TransactionType {
            return TransactionType.valueOf(dto.name)
        }

        fun toDto(model: TransactionType): GWShareJournalDto.TransactionType {
            return GWShareJournalDto.TransactionType.valueOf(model.name)
        }
    }
}