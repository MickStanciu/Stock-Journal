package com.example.tradelog.api.rest.converter

import com.example.tradelog.api.core.model.ActionType
import com.example.tradelog.api.core.model.ShareJournalModel
import com.example.tradelog.api.spec.model.ShareJournalDto

class ShareJournalModelConverter {

    companion object {
        fun toModel(dto: ShareJournalDto): ShareJournalModel {
            return ShareJournalModel(
                    transactionDetails = TransactionModelConverter.toModel(dto.transactionDetails),
                    price = dto.price,
                    actualPrice = dto.actualPrice,
                    quantity = dto.quantity,
                    action = ActionType.lookup(dto.action.name)
            )
        }

        fun toDto(model: ShareJournalModel): ShareJournalDto {
            return ShareJournalDto.newBuilder()
                    .setTransactionDetails(TransactionModelConverter.toDto(model.transactionDetails))
                    .setPrice(model.price)
                    .setActualPrice(model.actualPrice)
                    .setQuantity(model.quantity)
                    .setAction(com.example.tradelog.api.spec.model.ActionType.valueOf(model.action.name))
                    .build()
        }
    }
}