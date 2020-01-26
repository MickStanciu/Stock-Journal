package com.example.tradelog.api.rest.converter

import com.example.tradelog.api.core.model.ActionType
import com.example.tradelog.api.core.model.ShareJournalModel
import com.example.tradelog.api.spec.model.TLShareJournalDto

class ShareJournalModelConverter {

    companion object {
        fun toModel(dto: TLShareJournalDto): ShareJournalModel {
            return ShareJournalModel(
                    transactionDetails = TransactionModelConverter.toModel(dto.transactionDetails),
                    price = dto.price,
                    quantity = dto.quantity,
                    action = ActionType.lookup(dto.action.name)
            )
        }

        fun toDto(model: ShareJournalModel): TLShareJournalDto {
            return TLShareJournalDto.newBuilder()
                    .setTransactionDetails(TransactionModelConverter.toDto(model.transactionDetails))
                    .setPrice(model.price)
                    .setQuantity(model.quantity)
                    .setAction(TLShareJournalDto.ActionType.valueOf(model.action.name))
                    .build()
        }
    }
}
