package com.example.tradelog.api.rest.converter


class TransactionSettingsModelConverter {

    companion object {
        fun toModel(spec: com.example.tradelog.api.spec.model.TransactionSettingsModel): com.example.tradelog.api.core.model.TransactionSettingsModel {
            return com.example.tradelog.api.core.model.TransactionSettingsModel(
                    transactionId = spec.transactionId,
                    preferredPrice = spec.preferredPrice,
                    groupSelected = spec.groupSelected,
                    legClosed = spec.legClosed
                    )
        }
    }
}
