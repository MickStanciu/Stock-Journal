package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.DividendJournalModel
import com.example.gateway.api.spec.model.GWDividendJournalDto
import com.example.tradelog.api.spec.model.TLDividendJournalDto
import com.example.tradelog.api.spec.model.TLTransactionDto
import com.example.tradelog.api.spec.model.TLTransactionSettingsDto

class DividendJournalConverter {

    companion object {
        fun toModel(dto: TLDividendJournalDto): DividendJournalModel {
            return DividendJournalModel(
                    transactionId = dto.transactionDetails.id,
                    accountId = dto.transactionDetails.accountId,
                    portfolioId = dto.transactionDetails.portfolioId,
                    symbol = dto.transactionDetails.symbol,
                    date = TimeConverter.toOffsetDateTime(dto.transactionDetails.date),
                    dividend = dto.dividend,
                    quantity = dto.quantity,
                    groupSelected = dto.transactionDetails.settings.groupSelected,
                    legClosed = dto.transactionDetails.settings.legClosed
            )
        }

        fun toGWDto(model: DividendJournalModel): GWDividendJournalDto {
            return GWDividendJournalDto.newBuilder()
                    .setTransactionId(model.transactionId)
                    .setDate(model.date.toString())
                    .setSymbol(model.symbol)
                    .setDividend(model.dividend)
                    .setQuantity(model.quantity)
                    .setGroupSelected(model.groupSelected)
                    .setLegClosed(model.legClosed)
                    .build()
        }

        fun toTLDto(model: DividendJournalModel): TLDividendJournalDto {
            return TLDividendJournalDto.newBuilder()
                    .setTransactionDetails(
                            TLTransactionDto.newBuilder()
                                    .setId(model.transactionId)
                                    .setAccountId(model.accountId)
                                    .setPortfolioId(model.portfolioId)
                                    .setDate(model.date.toString())
                                    .setSymbol(model.symbol)
                                    .setBrokerFees(0.00)
                                    .setType(TLTransactionDto.TransactionType.DIVIDEND)
                                    .setSettings(
                                            TLTransactionSettingsDto.newBuilder()
                                                    .setPreferredPrice(0.00)
                                                    .setGroupSelected(model.groupSelected)
                                                    .setLegClosed(model.legClosed)
                                                    .build()
                                    )
                                    .build()
                    )
                    .setDividend(model.dividend)
                    .setQuantity(model.quantity)
                    .build()
        }
    }

}
