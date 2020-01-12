package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.ActionType
import com.example.gateway.api.core.model.OptionJournalModel
import com.example.gateway.api.core.model.OptionType
import com.example.gateway.api.core.model.TransactionType
import com.example.gateway.api.spec.model.CreateOptionJournalDto as GWCreateOptionJournalDto

class CreateOptionJournalConverter {

    companion object {
        fun toModel(accountId: String, dto: GWCreateOptionJournalDto): OptionJournalModel {
            return OptionJournalModel(
                    transactionId = "",
                    accountId = accountId,
                    date = TimeConverter.toOffsetDateTime.apply(dto.date),
                    stockSymbol = dto.stockSymbol,
                    stockPrice = dto.stockPrice,
                    strikePrice = dto.strikePrice,
                    premium = dto.premium,
                    expiryDate = TimeConverter.toOffsetDateTime.apply(dto.expiryDate),
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