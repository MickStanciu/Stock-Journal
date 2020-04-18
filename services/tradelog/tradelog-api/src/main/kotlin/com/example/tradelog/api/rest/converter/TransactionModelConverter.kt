package com.example.tradelog.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.tradelog.api.core.model.TransactionModel
import com.example.tradelog.api.core.model.TransactionType
import com.example.tradelog.api.spec.model.TLTransactionDto

class TransactionModelConverter {

    companion object {

        fun toModel(dto: TLTransactionDto): TransactionModel {
            return TransactionModel(
                    id = dto.id,
                    accountId = dto.accountId,
                    portfolioId = dto.portfolioId,
                    date = TimeConverter.toOffsetDateTime.apply(dto.date),
                    symbol = dto.symbol,
                    type = TransactionType.lookup(dto.type.name),
                    brokerFees = dto.brokerFees,
                    settings = TransactionSettingsModelConverter.toModel(dto.settings)
            )
        }

        fun toDto(model: TransactionModel): TLTransactionDto {
            return TLTransactionDto.newBuilder()
                    .setId(model.id)
                    .setAccountId(model.accountId)
                    .setDate(model.date.toString())
                    .setSymbol(model.symbol)
                    .setType(TLTransactionDto.TransactionType.valueOf(model.type.name))
                    .setBrokerFees(model.brokerFees)
                    .setSettings(TransactionSettingsModelConverter.toDto(model.settings))
                    .build()
        }
    }
}
