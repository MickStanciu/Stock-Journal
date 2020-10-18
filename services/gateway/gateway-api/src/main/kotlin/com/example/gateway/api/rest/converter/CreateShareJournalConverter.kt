package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.ActionType
import com.example.gateway.api.core.model.ShareJournalModel
import com.example.gateway.api.core.model.SupportMetadata
import com.example.gateway.api.core.model.TransactionType
import com.example.gateway.api.spec.model.GWCreateShareJournalDto

class CreateShareJournalConverter {

    companion object {
        fun toModel(supportMetadata: SupportMetadata, dto: GWCreateShareJournalDto): ShareJournalModel {
            return ShareJournalModel(
                    transactionId = null,
                    accountId = supportMetadata.accountId,
                    portfolioId = supportMetadata.portfolioId,
                    date = TimeConverter.toOffsetDateTime(dto.date),
                    symbol = dto.symbol,
                    price = dto.price,
                    preferredPrice = 0.00,
                    actualPrice = 0.00,
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