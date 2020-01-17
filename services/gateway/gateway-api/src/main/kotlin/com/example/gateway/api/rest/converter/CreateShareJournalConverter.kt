package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.ActionType
import com.example.gateway.api.core.model.ShareJournalModel
import com.example.gateway.api.core.model.TransactionType
import com.example.gateway.api.spec.model.GWCreateShareJournalDto

class CreateShareJournalConverter {

    companion object {
        fun toModel(accountId: String, dto: GWCreateShareJournalDto): ShareJournalModel {
            return ShareJournalModel(
                    transactionId = "",
                    accountId = accountId,
                    date = TimeConverter.toOffsetDateTime.apply(dto.date),
                    symbol = dto.symbol,
                    price = dto.price,
                    preferredPrice = 0.00,
                    quantity = dto.quantity,
                    brokerFees = dto.brokerFees,
                    groupSelected = false,
                    legClosed = false,
                    transactionType = TransactionType.SHARE,
                    action = ActionType.lookup(dto.action.name)
            )
        }
    }
}