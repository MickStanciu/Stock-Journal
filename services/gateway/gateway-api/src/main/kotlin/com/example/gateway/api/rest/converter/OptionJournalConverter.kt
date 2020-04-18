package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.ActionType
import com.example.gateway.api.core.model.OptionJournalModel
import com.example.gateway.api.core.model.OptionType
import com.example.gateway.api.core.model.TransactionType
import com.example.gateway.api.spec.model.GWOptionJournalDto
import com.example.tradelog.api.spec.model.TLOptionJournalDto
import com.example.tradelog.api.spec.model.TLTransactionDto
import com.example.tradelog.api.spec.model.TLTransactionSettingsDto

class OptionJournalConverter {

    companion object {
        fun toModel(dto: TLOptionJournalDto): OptionJournalModel {
            return OptionJournalModel(
                    transactionId = dto.transactionDetails.id,
                    accountId = dto.transactionDetails.accountId,
                    portfolioId = dto.transactionDetails.portfolioId,
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

        fun toModel(accountId: String, dto: GWOptionJournalDto): OptionJournalModel {
            return OptionJournalModel(
                    transactionId = dto.transactionId,
                    accountId = accountId,
                    portfolioId = "", /* NOT USED */
                    stockSymbol = dto.stockSymbol,
                    stockPrice = dto.stockPrice,
                    strikePrice = dto.strikePrice,
                    contracts = dto.contracts,
                    premium = dto.premium,
                    expiryDate = TimeConverter.toOffsetDateTime.apply(dto.expiryDate),
                    date = TimeConverter.toOffsetDateTime.apply(dto.date),
                    brokerFees = dto.brokerFees,
                    groupSelected = dto.groupSelected,
                    legClosed = dto.legClosed,
                    optionType = OptionType.lookup(dto.optionType.name),
                    transactionType = TransactionType.valueOf(dto.type.name),
                    action = ActionType.lookup(dto.action.name)
            )
        }

        fun toGWDto(model: OptionJournalModel): GWOptionJournalDto {
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

        fun toTLDto(model: OptionJournalModel): TLOptionJournalDto {
            return TLOptionJournalDto.newBuilder()
                    .setTransactionDetails(
                            TLTransactionDto.newBuilder()
                                    .setId(model.transactionId)
                                    .setAccountId(model.accountId)
                                    .setPortfolioId(model.portfolioId)
                                    .setDate(model.date.toString())
                                    .setSymbol(model.stockSymbol)
                                    .setBrokerFees(model.brokerFees)
                                    .setType(TLTransactionDto.TransactionType.valueOf(model.transactionType.name))
                                    .setSettings(
                                            TLTransactionSettingsDto.newBuilder()
                                                    .setPreferredPrice(0.00)
                                                    .setGroupSelected(model.groupSelected)
                                                    .setLegClosed(model.legClosed)
                                                    .build()
                                    )
                                    .build()
                    )
                    .setStrikePrice(model.strikePrice)
                    .setContracts(model.contracts)
                    .setPremium(model.premium)
                    .setExpiryDate(model.expiryDate.toString())
                    .setOptionType(TLOptionJournalDto.OptionType.valueOf(model.optionType.name))
                    .setAction(TLOptionJournalDto.ActionType.valueOf(model.action.name))
                    .build()
        }

    }
}