package com.example.tradelog.api.rest.validator

import com.example.common.validator.FieldValidator
import com.example.tradelog.api.spec.model.*

class RequestValidator: FieldValidator() {

    companion object {
        fun validateGetAllTradedSymbols(accountId: String): Boolean {
            return FieldValidator.accountId.test(accountId)
        }

        fun validateGetSummary(accountId: String): Boolean {
            return FieldValidator.accountId.test(accountId)
        }

        fun validateUpdateSettings(accountId: String, transactionId: String, dto: TransactionSettingsDto): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && transactionId == dto.transactionId
                    && FieldValidator.transactionId.test(transactionId)
                    && validateTransactionSettingsModel(dto)

        }

        fun validateGetAllBySymbol(accountId: String, symbol: String): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && FieldValidator.symbol.test(symbol)
        }

        fun validateCreateShareRecord(accountId: String, dto: ShareJournalDto): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && validateShareJournalModel(dto)
        }

        fun validateEditShareRecord(accountId: String, transactionId: String, dto: ShareJournalDto): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && transactionId == dto.transactionDetails.id
                    && FieldValidator.transactionId.test(transactionId)
                    && validateShareJournalModel(dto)
        }


        fun validateDeleteShareRecord(accountId: String, transactionId: String): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && FieldValidator.transactionId.test(transactionId)
        }

        fun validateCreateOptionRecord(accountId: String, dto: OptionJournalDto): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && validateOptionJournalModel(dto)
        }

        fun validateEditOptionRecord(accountId: String, transactionId: String, dto: OptionJournalDto): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && transactionId == dto.transactionDetails.id
                    && FieldValidator.transactionId.test(transactionId)
                    && validateOptionJournalModel(dto)
        }

        fun validateDeleteOptionRecord(accountId: String, transactionId: String): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && FieldValidator.transactionId.test(transactionId)
        }


        fun validateCreateDividendRecord(accountId: String, dto: DividendJournalDto): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && validateDividendJournalModel(dto)
        }

        fun validateEditDividendRecord(accountId: String, transactionId: String, dto: DividendJournalDto): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && transactionId == dto.transactionDetails.id
                    && validateDividendJournalModel(dto)
        }

        fun validateDeleteDividendRecord(accountId: String, transactionId: String): Boolean {
            return FieldValidator.accountId.test(accountId)
                    && FieldValidator.transactionId.test(transactionId)
        }

        fun validateDividendJournalModel(dto: DividendJournalDto): Boolean {
            return (dto.transactionDetails != null
                    && dto.transactionDetails.type === TransactionDto.TransactionType.DIVIDEND
                    && dto.transactionDetails.date != null
                    && symbol.test(dto.transactionDetails.symbol)
                    && FieldValidator.accountId.test(dto.transactionDetails.accountId)
                    && dto.quantity >= 0
                    && dto.dividend >= 0)
        }

        fun validateShareJournalModel(dto: ShareJournalDto): Boolean {
            return (dto.transactionDetails != null
                    && dto.transactionDetails.type === TransactionDto.TransactionType.SHARE
                    && dto.transactionDetails.date != null
                    && symbol.test(dto.transactionDetails.symbol)
                    && FieldValidator.accountId.test(dto.transactionDetails.accountId)
                    && dto.action != null
                    && dto.action !== ShareJournalDto.ActionType.UNKNOWN_ACTION_TYPE
                    && dto.price >= 0.00 && dto.quantity >= 1)
        }

        fun validateOptionJournalModel(dto: OptionJournalDto): Boolean {
            return (dto.transactionDetails != null
                    && dto.transactionDetails.type === TransactionDto.TransactionType.OPTION
                    && dto.transactionDetails.date != null
                    && symbol.test(dto.transactionDetails.symbol)
                    && FieldValidator.accountId.test(dto.transactionDetails.accountId)
                    && dto.action != null
                    && dto.action !== OptionJournalDto.ActionType.UNKNOWN_ACTION_TYPE
                    && (dto.optionType === OptionJournalDto.OptionType.CALL || dto.optionType === OptionJournalDto.OptionType.PUT)
                    && dto.contracts > 0
                    && dto.premium >= 0
                    && dto.expiryDate != null
                    && dto.stockPrice >= 0.00
                    && dto.strikePrice > 0)
        }

        fun validateTransactionSettingsModel(dto: TransactionSettingsDto): Boolean {
            return FieldValidator.transactionId.test(dto.transactionId)
        }

    }
}
