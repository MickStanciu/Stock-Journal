package com.example.tradelog.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.tradelog.api.core.model.TransactionModel
import com.example.tradelog.api.core.model.TransactionType
import com.example.tradelog.api.spec.model.TLTransactionDto
import java.util.*

class TransactionModelConverter {

    companion object {

        fun toModel(dto: TLTransactionDto): TransactionModel {
            return TransactionModel(
                    id = if (dto.id == null || dto.id.isEmpty() || "null" == dto.id) { UUID.randomUUID() } else { UUID.fromString(dto.id) },
                    accountId = UUID.fromString(dto.accountId),
                    portfolioId = UUID.fromString(dto.portfolioId),
                    date = TimeConverter.toOffsetDateTime(dto.date),
                    symbol = dto.symbol,
                    type = TransactionType.lookup(dto.type.name),
                    brokerFees = dto.brokerFees,
                    settings = TransactionSettingsModelConverter.toModel(dto.settings)
            )
        }

        fun toDto(model: TransactionModel): TLTransactionDto {
            return TLTransactionDto.newBuilder()
                    .setId(model.id.toString())
                    .setAccountId(model.accountId.toString())
                    .setPortfolioId(model.portfolioId.toString())
                    .setDate(model.date.toString())
                    .setSymbol(model.symbol)
                    .setType(TLTransactionDto.TransactionType.valueOf(model.type.name))
                    .setBrokerFees(model.brokerFees)
                    .setSettings(TransactionSettingsModelConverter.toDto(model.settings))
                    .build()
        }
    }
}
