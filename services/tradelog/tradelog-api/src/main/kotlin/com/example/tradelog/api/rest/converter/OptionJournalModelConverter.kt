package com.example.tradelog.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.tradelog.api.core.model.ActionType
import com.example.tradelog.api.core.model.OptionJournalModel
import com.example.tradelog.api.core.model.OptionType
import com.example.tradelog.api.spec.model.OptionJournalDto

class OptionJournalModelConverter {

    companion object {
        fun toModel(dto: OptionJournalDto): OptionJournalModel {
            return OptionJournalModel(
                    transactionDetails = TransactionModelConverter.toModel(dto.transactionDetails),
                    stockPrice = dto.stockPrice,
                    action = ActionType.lookup(dto.action.name),
                    contracts = dto.contracts,
                    expiryDate = TimeConverter.toOffsetDateTime.apply(dto.expiryDate),
                    optionType = OptionType.lookup(dto.optionType.name),
                    premium = dto.premium,
                    strikePrice = dto.strikePrice
            )
        }

        fun toDto(model: OptionJournalModel): OptionJournalDto {
            return OptionJournalDto.newBuilder()
                    .setTransactionDetails(TransactionModelConverter.toDto(model.transactionDetails))
                    .setStockPrice(model.stockPrice)
                    .setAction(OptionJournalDto.ActionType.valueOf(model.action.name))
                    .setContracts(model.contracts)
                    .setExpiryDate(model.expiryDate.toString())
                    .setOptionType(OptionJournalDto.OptionType.valueOf(model.optionType.name))
                    .setPremium(model.premium)
                    .setStrikePrice(model.strikePrice)
                    .build()
        }
    }
}
