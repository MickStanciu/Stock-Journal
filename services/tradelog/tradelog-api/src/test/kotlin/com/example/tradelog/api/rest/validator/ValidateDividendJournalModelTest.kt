package com.example.tradelog.api.rest.validator

import com.example.tradelog.api.spec.model.DividendJournalDto
import com.example.tradelog.api.spec.model.TransactionDto
import com.example.tradelog.api.spec.model.TransactionSettingsDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class ValidateDividendJournalModelTest {

    @Test
    fun testValidateDividendJournalModelWhenDividendIsNegative() {
        val transactionSettingsDto = TransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TransactionDto.newBuilder()
                .setId("1234")
                .setDate(OffsetDateTime.now().toString())
                .setAccountId("123456789012345678901234567890123456")
                .setSymbol("XYZ")
                .setType(TransactionDto.TransactionType.DIVIDEND)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val dividendJournalDto = DividendJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setQuantity(10)
                .setDividend(-1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateDividendJournalModel(dividendJournalDto))
    }


    @Test
    fun testValidateDividendJournalModelWhenQuantityIsNegative() {
        val transactionSettingsDto = TransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TransactionDto.newBuilder()
                .setId("1234")
                .setDate(OffsetDateTime.now().toString())
                .setAccountId("123456789012345678901234567890123456")
                .setSymbol("XYZ")
                .setType(TransactionDto.TransactionType.DIVIDEND)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val dividendJournalDto = DividendJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setQuantity(-10)
                .setDividend(1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateDividendJournalModel(dividendJournalDto))
    }


    @Test
    fun testValidateDividendJournalModelWhenTransactionTypeIsNotDividend() {
        val transactionSettingsDto = TransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TransactionDto.newBuilder()
                .setId("1234")
                .setDate(OffsetDateTime.now().toString())
                .setAccountId("123456789012345678901234567890123456")
                .setSymbol("XYZ")
                .setType(TransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val dividendJournalDto = DividendJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setQuantity(10)
                .setDividend(1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateDividendJournalModel(dividendJournalDto))
    }


    @Test
    fun testValidateDividendJournalModelTruthy() {
        val transactionSettingsDto = TransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TransactionDto.newBuilder()
                .setId("1234")
                .setDate(OffsetDateTime.now().toString())
                .setAccountId("123456789012345678901234567890123456")
                .setSymbol("XYZ")
                .setType(TransactionDto.TransactionType.DIVIDEND)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val dividendJournalDto = DividendJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setQuantity(10)
                .setDividend(1.0)
                .build()

        Assertions.assertTrue(RequestValidator.validateDividendJournalModel(dividendJournalDto))
    }
}