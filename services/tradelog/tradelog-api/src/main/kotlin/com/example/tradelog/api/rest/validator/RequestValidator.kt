package com.example.tradelog.api.rest.validator

import com.example.common.validator.FieldValidator
import com.example.tradelog.api.spec.model.TransactionSettingsModel

class RequestValidator: FieldValidator() {

    companion object {
        fun validateGetAllTradedSymbols(accountId: String): Boolean {
            return FieldValidator.accountId.test(accountId)
        }

        fun validateGetSummary(accountId: String): Boolean {
            return FieldValidator.accountId.test(accountId)
        }

        fun validateUpdateSettings(accountId: String, transactionId: String, model: TransactionSettingsModel?): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && FieldValidator.transactionId.test(transactionId)
                    && model != null

        }
    }
}
