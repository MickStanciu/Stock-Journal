package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.ActionType
import com.example.gateway.api.core.model.ShareJournalModel
import com.example.gateway.api.core.model.TransactionType
import com.example.gateway.api.spec.model.ShareJournalDto as GWShareJournalDto
import com.example.tradelog.api.spec.model.ShareJournalDto as TLShareJournalDto
import com.example.tradelog.api.spec.model.TransactionDto as TLTransactionDto

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
                    transactionType = toTransactionTypeModel(dto.transactionDetails.type),
                    action = toActionTypeModel(dto.action)
            )
        }

        fun toDto(model: ShareJournalModel): GWShareJournalDto {
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
                    .setType(toTransactionTypeDto(model.transactionType))
                    .setAction(toActionTypeDto(model.action))
                    .build()
        }

        private fun toTransactionTypeModel(dto: TLTransactionDto.TransactionType): TransactionType {
            return TransactionType.valueOf(dto.name)
        }

        private fun toTransactionTypeDto(model: TransactionType): GWShareJournalDto.TransactionType {
            return GWShareJournalDto.TransactionType.valueOf(model.name)
        }

        private fun toActionTypeModel(dto: TLShareJournalDto.ActionType): ActionType {
            return ActionType.lookup(dto.name)
        }

        private fun toActionTypeDto(model: ActionType): GWShareJournalDto.ActionType {
            return GWShareJournalDto.ActionType.valueOf(model.name)
        }

    }
}