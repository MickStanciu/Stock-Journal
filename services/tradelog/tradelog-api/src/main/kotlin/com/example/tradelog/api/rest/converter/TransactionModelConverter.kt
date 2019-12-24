package com.example.tradelog.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.tradelog.api.core.model.TransactionModel
import com.example.tradelog.api.core.model.TransactionType
import com.example.tradelog.api.spec.model.TransactionDto

class TransactionModelConverter {

    companion object {

        fun toModel(dto: TransactionDto): TransactionModel {
            return TransactionModel(
                    id = dto.id,
                    accountId = dto.accountId,
                    date = TimeConverter.toOffsetDateTime.apply(dto.date),
                    symbol = dto.symbol,
                    type = TransactionType.lookup(dto.type.name),
                    brokerFees = dto.brokerFees,
                    settings = TransactionSettingsModelConverter.toModel(dto.settings)
            )
        }

        fun toDto(model: TransactionModel): TransactionDto {
            return TransactionDto.newBuilder()
                    .setId(model.id)
                    .setAccountId(model.accountId)
                    .setDate(model.date.toString())
                    .setSymbol(model.symbol)
                    .setType(TransactionDto.TransactionType.valueOf(model.type.name))
                    .setBrokerFees(model.brokerFees)
                    .setSettings(TransactionSettingsModelConverter.toDto(model.settings))
                    .build()
        }
    }
}
