package com.example.tradelog.api.rest.validator

import com.example.common.validator.FieldValidator
import com.example.tradelog.api.spec.model.*
import java.util.*

class RequestValidator: FieldValidator() {

    companion object {
        fun validateUpdateSettings(transactionId: UUID, dto: TLTransactionSettingsDto): Boolean {
            return transactionId.toString() == dto.transactionId
                    && validateTransactionSettingsModel(dto)
        }

        fun validateGetAllBySymbol(symbol: String): Boolean {
            return symbol(symbol)
        }

        fun validateCreateShareRecord(dto: TLShareJournalDto): Boolean {
            return validateShareJournalModel(dto)
        }

        fun validateEditShareRecord(transactionId: UUID, dto: TLShareJournalDto): Boolean {
            return transactionId.toString() == dto.transactionDetails.id
                    && validateShareJournalModel(dto)
        }

        fun validateCreateOptionRecord(dto: TLOptionJournalDto): Boolean {
            return validateOptionJournalModel(dto)
        }

        fun validateEditOptionRecord(transactionId: UUID, dto: TLOptionJournalDto): Boolean {
            return transactionId.toString() == dto.transactionDetails.id
                    && validateOptionJournalModel(dto)
        }

        fun validateCreateDividendRecord(dto: TLDividendJournalDto): Boolean {
            return validateDividendJournalModel(dto)
        }

        fun validateEditDividendRecord(transactionId: UUID, dto: TLDividendJournalDto): Boolean {
            return transactionId.toString() == dto.transactionDetails.id
                    && validateDividendJournalModel(dto)
        }

        fun validateDividendJournalModel(dto: TLDividendJournalDto): Boolean {
            return (dto.transactionDetails != null
                    && dto.transactionDetails.type === TLTransactionDto.TransactionType.DIVIDEND
                    && dto.transactionDetails.date != null
                    && symbol(dto.transactionDetails.symbol)
                    && UUID(dto.transactionDetails.accountId)
                    && dto.quantity >= 0
                    && dto.dividend >= 0)
        }

        fun validateShareJournalModel(dto: TLShareJournalDto): Boolean {
            return (dto.transactionDetails != null
                    && dto.transactionDetails.type === TLTransactionDto.TransactionType.SHARE
                    && dto.transactionDetails.date != null
                    && symbol(dto.transactionDetails.symbol)
                    && UUID(dto.transactionDetails.accountId)
                    && dto.action != null
                    && dto.action !== TLShareJournalDto.ActionType.UNKNOWN_ACTION_TYPE
                    && dto.price >= 0.00 && dto.quantity >= 1)
        }

        fun validateOptionJournalModel(dto: TLOptionJournalDto): Boolean {
            return (dto.transactionDetails != null
                    && dto.transactionDetails.type === TLTransactionDto.TransactionType.OPTION
                    && dto.transactionDetails.date != null
                    && symbol(dto.transactionDetails.symbol)
                    && UUID(dto.transactionDetails.accountId)
                    && dto.action != null
                    && dto.action !== TLOptionJournalDto.ActionType.UNKNOWN_ACTION_TYPE
                    && (dto.optionType === TLOptionJournalDto.OptionType.CALL || dto.optionType === TLOptionJournalDto.OptionType.PUT)
                    && dto.contracts > 0
                    && dto.premium >= 0
                    && dto.expiryDate != null
                    && dto.stockPrice >= 0.00
                    && dto.strikePrice > 0)
        }

        fun validateTransactionSettingsModel(dto: TLTransactionSettingsDto): Boolean {
            return UUID(dto.transactionId)
        }

    }
}
