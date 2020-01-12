package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.ActionType
import com.example.gateway.api.core.model.OptionJournalModel
import com.example.gateway.api.core.model.OptionType
import com.example.gateway.api.core.model.TransactionType
import com.example.gateway.api.spec.model.OptionJournalDto as GWOptionJournalDto
import com.example.tradelog.api.spec.model.OptionJournalDto as TLOptionJournalDto

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
                    optionType = OptionType.lookup(dto.optionType.name),
                    transactionType = TransactionType.valueOf(dto.transactionDetails.type.name),
                    action = ActionType.lookup(dto.action.name)
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
                    .setOptionType(GWOptionJournalDto.OptionType.valueOf(model.optionType.name))
                    .setType(GWOptionJournalDto.TransactionType.valueOf(model.transactionType.name))
                    .setAction(GWOptionJournalDto.ActionType.valueOf(model.action.name))
                    .build()
        }

    }
}