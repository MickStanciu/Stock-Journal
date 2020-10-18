package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.ActionType
import com.example.gateway.api.core.model.ShareJournalModel
import com.example.gateway.api.core.model.SupportMetadata
import com.example.gateway.api.core.model.TransactionType
import com.example.gateway.api.spec.model.GWShareJournalDto
import com.example.tradelog.api.spec.model.TLShareJournalDto
import com.example.tradelog.api.spec.model.TLTransactionDto
import com.example.tradelog.api.spec.model.TLTransactionSettingsDto
import java.util.*

class ShareJournalConverter {

    //TODO: missing actual price, but seems that web layer doesn't need it

    companion object {
        fun toModel(dto: TLShareJournalDto): ShareJournalModel {
            return ShareJournalModel(
                    transactionId = UUID.fromString(dto.transactionDetails.id),
                    accountId = UUID.fromString(dto.transactionDetails.accountId),
                    portfolioId = UUID.fromString(dto.transactionDetails.portfolioId),
                    date = TimeConverter.toOffsetDateTime(dto.transactionDetails.date),
                    symbol = dto.transactionDetails.symbol,
                    price = dto.price,
                    preferredPrice = dto.transactionDetails.settings.preferredPrice,
                    actualPrice = 0.00,
                    quantity = dto.quantity,
                    brokerFees = dto.transactionDetails.brokerFees,
                    groupSelected = dto.transactionDetails.settings.groupSelected,
                    legClosed = dto.transactionDetails.settings.legClosed,
                    transactionType = TransactionType.valueOf(dto.transactionDetails.type.name),
                    action = ActionType.lookup(dto.action.name)
            )
        }

        fun toModel(supportMetadata: SupportMetadata, dto: GWShareJournalDto): ShareJournalModel {
            return ShareJournalModel(
                    transactionId = UUID.fromString(dto.transactionId),
                    accountId = supportMetadata.accountId,
                    portfolioId = supportMetadata.portfolioId,
                    date = TimeConverter.toOffsetDateTime(dto.date),
                    symbol = dto.symbol,
                    price = dto.price,
                    preferredPrice = dto.preferredPrice,
                    actualPrice = 0.00,
                    quantity = dto.quantity,
                    brokerFees = dto.brokerFees,
                    groupSelected = dto.groupSelected,
                    legClosed = dto.legClosed,
                    transactionType = TransactionType.valueOf(dto.type.name),
                    action = ActionType.lookup(dto.action.name)
            )
        }

        fun toGWDto(model: ShareJournalModel): GWShareJournalDto {
            return GWShareJournalDto.newBuilder()
                    .setTransactionId(model.transactionId.toString())
                    .setDate(model.date.toString())
                    .setSymbol(model.symbol)
                    .setPrice(model.price)
                    .setActualPrice(model.actualPrice)
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
                                    .setId(model.transactionId.toString())
                                    .setAccountId(model.accountId.toString())
                                    .setPortfolioId(model.portfolioId.toString())
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