package com.example.tradelog.api.rest.converter

import com.example.tradelog.api.core.model.TransactionSettingsModel
import com.example.tradelog.api.spec.model.TLTransactionSettingsDto
import java.util.*


class TransactionSettingsModelConverter {

    companion object {

        fun toModel(dto: TLTransactionSettingsDto): TransactionSettingsModel {
            return TransactionSettingsModel(
                    transactionId = UUID.fromString(dto.transactionId),
                    preferredPrice = dto.preferredPrice,
                    groupSelected = dto.groupSelected,
                    legClosed = dto.legClosed
                    )
        }

        fun toDto(model: TransactionSettingsModel): TLTransactionSettingsDto {
            return TLTransactionSettingsDto.newBuilder()
                    .setTransactionId(model.transactionId.toString())
                    .setPreferredPrice(model.preferredPrice)
                    .setGroupSelected(model.groupSelected)
                    .setLegClosed(model.legClosed)
                    .build()
        }
    }
}
