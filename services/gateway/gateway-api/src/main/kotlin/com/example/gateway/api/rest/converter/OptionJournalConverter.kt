package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.ActionType
import com.example.gateway.api.core.model.OptionJournalModel
import com.example.gateway.api.core.model.TransactionType
import com.example.gateway.api.spec.model.OptionJournalDto as GWOptionJournalDto
import com.example.tradelog.api.spec.model.OptionJournalDto as TLOptionJournalDto
import com.example.tradelog.api.spec.model.TransactionDto as TLTransactionDto

class OptionJournalConverter {

    companion object {
        fun toModel(dto: TLOptionJournalDto): OptionJournalModel {
            return OptionJournalModel(
                    transactionId = dto.transactionDetails.id,
                    stockSymbol = dto.transactionDetails.symbol,
                    stockPrice = dto.stockPrice,
                    strikePrice = dto.strikePrice,
                    contracts = dto.contracts,
                    premium = dto.premium,
                    expiryDate = TimeConverter.toOffsetDateTime.apply(dto.expiryDate),
                    date = TimeConverter.toOffsetDateTime.apply(dto.transactionDetails.date),
                    brokerFees = dto.transactionDetails.brokerFees,
                    groupSelected = dto.transactionDetails.settings.groupSelected,
                    legClosed = dto.transactionDetails.settings.legClosed,
                    transactionType = toTransactionTypeModel(dto.transactionDetails.type),
                    action = toActionTypeModel(dto.action)
            )
        }

        fun toDto(model: OptionJournalModel): GWOptionJournalDto {
            return GWOptionJournalDto.newBuilder()
                    .setTransactionId(model.transactionId)
                    .setStockSymbol(model.stockSymbol)
                    .setStockPrice(model.stockPrice)
                    .setStrikePrice(model.strikePrice)
                    .setContracts(model.contracts)
                    .setPremium(model.premium)
                    .setExpiryDate(model.expiryDate.toString())
                    .setDate(model.date.toString())
                    .setBrokerFees(model.brokerFees)
                    .setLegClosed(model.legClosed)
                    .setGroupSelected(model.groupSelected)
                    .setType(toTransactionTypeDto(model.transactionType))
                    .setAction(toActionTypeDto(model.action))
                    .build()
        }

        private fun toTransactionTypeModel(dto: TLTransactionDto.TransactionType): TransactionType {
            return TransactionType.valueOf(dto.name)
        }

        private fun toTransactionTypeDto(model: TransactionType): GWOptionJournalDto.TransactionType {
            return GWOptionJournalDto.TransactionType.valueOf(model.name)
        }

        private fun toActionTypeModel(dto: TLOptionJournalDto.ActionType): ActionType {
            return ActionType.lookup(dto.name)
        }


        private fun toActionTypeDto(model: ActionType): GWOptionJournalDto.ActionType {
            return GWOptionJournalDto.ActionType.valueOf(model.name)
        }
    }
}