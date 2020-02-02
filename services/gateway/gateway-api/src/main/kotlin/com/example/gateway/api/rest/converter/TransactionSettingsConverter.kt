package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.TransactionSettingsModel
import com.example.gateway.api.spec.model.GWTransactionSettingsBulkDto
import com.example.gateway.api.spec.model.GWTransactionSettingsDto

class TransactionSettingsConverter {

    companion object {
        fun toModel(dto: GWTransactionSettingsDto): TransactionSettingsModel {
            return TransactionSettingsModel(
                    transactionId = dto.transactionId,
                    preferredPrice = dto.preferredPrice,
                    legClosed = dto.legClosed,
                    groupSelected = dto.groupSelected)
        }

        fun toDto(model: TransactionSettingsModel): GWTransactionSettingsDto {
            return GWTransactionSettingsDto.newBuilder()
                    .setTransactionId(model.transactionId)
                    .setPreferredPrice(model.preferredPrice)
                    .setGroupSelected(model.groupSelected)
                    .setLegClosed(model.legClosed)
                    .build()
        }

        fun toModels(dto: GWTransactionSettingsBulkDto): List<TransactionSettingsModel> {
            return dto.itemsList.map { this.toModel(it) }.toList()
        }
    }
}