package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.ActionType
import com.example.gateway.api.core.model.ShareJournalModel
import com.example.gateway.api.core.model.TransactionType
import com.example.gateway.api.spec.model.ShareJournalDto as GWShareJournalDto
import com.example.tradelog.api.spec.model.ShareJournalDto as TLShareJournalDto
import com.example.tradelog.api.spec.model.TransactionDto as TLTransactionDto
import com.example.tradelog.api.spec.model.TransactionSettingsDto as TLTransactionSettingsDto

class ShareJournalConverter {

    //TODO: missing actual price, but seems that web layer doesn't need it

    companion object {
        fun toModel(dto: TLShareJournalDto): ShareJournalModel {
            return ShareJournalModel(
                    transactionId = dto.transactionDetails.id,
                    accountId = dto.transactionDetails.accountId,
                    date = TimeConverter.toOffsetDateTime.apply(dto.transactionDetails.date),
                    symbol = dto.transactionDetails.symbol,
                    price = dto.price,
                    preferredPrice = dto.transactionDetails.settings.preferredPrice,
                    quantity = dto.quantity,
                    brokerFees = dto.transactionDetails.brokerFees,
                    groupSelected = dto.transactionDetails.settings.groupSelected,
                    legClosed = dto.transactionDetails.settings.legClosed,
                    transactionType = TransactionType.valueOf(dto.transactionDetails.type.name),
                    action = ActionType.lookup(dto.action.name)
            )
        }

        fun toGWDto(model: ShareJournalModel): GWShareJournalDto {
            return GWShareJournalDto.newBuilder()
                    .setTransactionId(model.transactionId)
                    .setDate(model.date.toString())
                    .setSymbol(model.symbol)
                    .setPrice(model.price)
                    .setPreferredPrice(model.preferredPrice)
                    .setQuantity(model.quantity)
                    .setBrokerFees(model.brokerFees)
                    .setGroupSelected(model.groupSelected)
                    .setLegClosed(model.legClosed)
                    .setType(GWShareJournalDto.TransactionType.valueOf(model.transactionType.name))
                    .setAction(GWShareJournalDto.ActionType.valueOf(model.action.name))
                    .build()
        }

        fun toTLDto(model: ShareJournalModel): TLShareJournalDto {
            return TLShareJournalDto.newBuilder()
                    .setTransactionDetails(
                            TLTransactionDto.newBuilder()
                                    .setId(model.transactionId)
                                    .setAccountId(model.accountId)
                                    .setDate(model.date.toString())
                                    .setSymbol(model.symbol)
                                    .setBrokerFees(model.brokerFees)
                                    .setType(TLTransactionDto.TransactionType.valueOf(model.transactionType.name))
                                    .setSettings(
                                            TLTransactionSettingsDto.newBuilder()
                                                    .setPreferredPrice(model.preferredPrice)
                                                    .setGroupSelected(model.groupSelected)
                                                    .setLegClosed(model.legClosed)
                                                    .build()
                                    )
                                    .build()
                    )
                    .setPrice(model.price)
                    .setQuantity(model.quantity)
                    .setAction(TLShareJournalDto.ActionType.valueOf(model.action.name))
                    .build()
        }

    }
}