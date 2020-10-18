package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.*
import com.example.gateway.api.spec.model.GWCreateOptionJournalDto

class CreateOptionJournalConverter {

    companion object {
        fun toModel(supportMetadata: SupportMetadata, dto: GWCreateOptionJournalDto): OptionJournalModel {
            return OptionJournalModel(
                    transactionId = null,
                    accountId = supportMetadata.accountId,
                    portfolioId = supportMetadata.portfolioId,
                    date = TimeConverter.toOffsetDateTime(dto.date),
                    stockSymbol = dto.stockSymbol,
                    stockPrice = dto.stockPrice,
                    strikePrice = dto.strikePrice,
                    premium = dto.premium,
                    expiryDate = TimeConverter.toOffsetDateTime(dto.expiryDate),
                    contracts = dto.contracts,
                    optionType = OptionType.lookup(dto.optionType.name),
                    legClosed = false,
                    groupSelected = false,
                    brokerFees = dto.brokerFees,
                    transactionType = TransactionType.OPTION,
                    action = ActionType.lookup(dto.action.name)
            )
        }

    }
}