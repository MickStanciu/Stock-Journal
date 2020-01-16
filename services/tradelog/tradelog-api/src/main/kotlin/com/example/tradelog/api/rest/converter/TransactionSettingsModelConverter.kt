package com.example.tradelog.api.rest.converter

import com.example.tradelog.api.core.model.TransactionSettingsModel
import com.example.tradelog.api.spec.model.TLTransactionSettingsDto


class TransactionSettingsModelConverter {

    companion object {

        fun toModel(dto: TLTransactionSettingsDto): TransactionSettingsModel {
            return TransactionSettingsModel(
                    transactionId = dto.transactionId,
                    preferredPrice = dto.preferredPrice,
                    groupSelected = dto.groupSelected,
                    legClosed = dto.legClosed
                    )
        }

        fun toDto(model: TransactionSettingsModel): TLTransactionSettingsDto {
            return TLTransactionSettingsDto.newBuilder()
                    .setTransactionId(model.transactionId)
                    .setPreferredPrice(model.preferredPrice)
                    .setGroupSelected(model.groupSelected)
                    .setLegClosed(model.legClosed)
                    .build()
        }
    }
}
