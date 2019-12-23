package com.example.tradelog.api.rest.validator

import com.example.common.validator.FieldValidator
import com.example.tradelog.api.spec.model.ShareJournalModel
import com.example.tradelog.api.spec.model.TransactionModel
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

        fun validateGetAllBySymbol(accountId: String, symbol: String): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && FieldValidator.symbol.test(symbol)
        }

        fun validateCreateShareRecord(accountId: String, dto: ShareJournalModel): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && validateShareJournalModel(dto)
        }

        fun validateEditShareRecord(accountId: String, transactionId: String, dto: ShareJournalModel): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && FieldValidator.transactionId.test(transactionId)
                    && validateShareJournalModel(dto)
        }


        fun validateDeleteShareRecord(accountId: String, transactionId: String): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && FieldValidator.transactionId.test(transactionId)
        }

        private fun validateShareJournalModel(dto: ShareJournalModel): Boolean {
            return (dto.transactionDetails != null
                    && dto.transactionDetails.type == TransactionModel.TransactionType.SHARE
                    && dto.transactionDetails.date != null
                    && symbol.test(dto.transactionDetails.symbol)
                    && FieldValidator.accountId.test(dto.transactionDetails.accountId)
                    && dto.action != null
                    && dto.action !== com.example.tradelog.api.spec.model.ActionType.UNKNOWN
                    && dto.price >= 0.00 && dto.quantity >= 1)
        }


    }
}
