package com.example.tradelog.api.rest.validator

import com.example.common.validator.FieldValidator
import com.example.tradelog.api.spec.model.*
import com.example.tradelog.api.validator.RequestValidation

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

        fun validateCreateOptionRecord(accountId: String, dto: OptionJournalModel): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && validateOptionJournalModel(dto)
        }

        fun validateDeleteOptionRecord(accountId: String, transactionId: String): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && FieldValidator.transactionId.test(transactionId)
        }


        fun validateCreateDividendRecord(accountId: String, dto: DividendJournalModel): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && validateDividendJournalModel(dto)
        }

        fun validateDeleteDividendRecord(accountId: String, transactionId: String): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && FieldValidator.transactionId.test(transactionId)
        }

        private fun validateDividendJournalModel(dto: DividendJournalModel): Boolean {
            return (dto.transactionDetails != null
                    && dto.transactionDetails.type === TransactionModel.TransactionType.DIVIDEND
                    && dto.transactionDetails.date != null
                    && symbol.test(dto.transactionDetails.symbol)
                    && RequestValidation.accountId.test(dto.transactionDetails.accountId)
                    && dto.quantity >= 0
                    && dto.dividend >= 0)
        }

        private fun validateShareJournalModel(dto: ShareJournalModel): Boolean {
            return (dto.transactionDetails != null
                    && dto.transactionDetails.type === TransactionModel.TransactionType.SHARE
                    && dto.transactionDetails.date != null
                    && symbol.test(dto.transactionDetails.symbol)
                    && FieldValidator.accountId.test(dto.transactionDetails.accountId)
                    && dto.action != null
                    && dto.action !== ActionType.UNKNOWN
                    && dto.price >= 0.00 && dto.quantity >= 1)
        }

        private fun validateOptionJournalModel(dto: OptionJournalModel): Boolean {
            return (dto.transactionDetails != null
                    && dto.transactionDetails.type === TransactionModel.TransactionType.OPTION
                    && dto.transactionDetails.date != null
                    && symbol.test(dto.transactionDetails.symbol)
                    && RequestValidation.accountId.test(dto.transactionDetails.accountId)
                    && dto.action != null
                    && dto.action !== ActionType.UNKNOWN
                    && (dto.optionType === OptionJournalModel.OptionType.CALL || dto.optionType === OptionJournalModel.OptionType.PUT)
                    && dto.contracts > 0
                    && dto.premium >= 0
                    && dto.expiryDate != null
                    && dto.stockPrice >= 0.00
                    && dto.strikePrice > 0)
        }




    }
}
