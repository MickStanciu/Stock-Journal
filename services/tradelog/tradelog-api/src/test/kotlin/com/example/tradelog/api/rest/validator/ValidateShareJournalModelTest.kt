package com.example.tradelog.api.rest.validator

import com.example.tradelog.api.spec.model.ShareJournalDto
import com.example.tradelog.api.spec.model.TransactionDto
import com.example.tradelog.api.spec.model.TransactionSettingsDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class ValidateShareJournalModelTest {

    @Test
    fun testValidateShareJournalModelWhenActionIsInvalid() {
        val transactionSettingsDto = TransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TransactionDto.newBuilder()
                .setId("1234")
                .setAccountId("123")
                .setDate(OffsetDateTime.now().toString())
                .setAccountId("123456789012345678901234567890123456")
                .setSymbol("XYZ")
                .setType(TransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = ShareJournalDto.newBuilder()
                .setAction(ShareJournalDto.ActionType.UNKNOWN_ACTION_TYPE)
                .setActualPrice(1.0)
                .setPrice(1.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelWhenAccountIsEmpty() {
        val transactionSettingsDto = TransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TransactionDto.newBuilder()
                .setId("1234")
                .setDate(OffsetDateTime.now().toString())
                .setSymbol("XYZ")
                .setType(TransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = ShareJournalDto.newBuilder()
                .setAction(ShareJournalDto.ActionType.BUY)
                .setActualPrice(1.0)
                .setPrice(1.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelWhenPriceIsNegative() {
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

        val shareJournalDto = ShareJournalDto.newBuilder()
                .setAction(ShareJournalDto.ActionType.BUY)
                .setActualPrice(1.0)
                .setPrice(-11.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelWhenQuantityIsZero() {
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

        val shareJournalDto = ShareJournalDto.newBuilder()
                .setAction(ShareJournalDto.ActionType.BUY)
                .setActualPrice(1.0)
                .setPrice(1.0)
                .setQuantity(-1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelWhenSymbolIsEmpty() {
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
                .setSymbol("")
                .setType(TransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = ShareJournalDto.newBuilder()
                .setAction(ShareJournalDto.ActionType.BUY)
                .setActualPrice(1.0)
                .setPrice(1.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelWhenSymbolIsSpace() {
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
                .setSymbol(" ")
                .setType(TransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = ShareJournalDto.newBuilder()
                .setAction(ShareJournalDto.ActionType.BUY)
                .setActualPrice(1.0)
                .setPrice(1.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelWhenTransactionTypeIsNotStock() {
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
                .setType(TransactionDto.TransactionType.OPTION)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = ShareJournalDto.newBuilder()
                .setAction(ShareJournalDto.ActionType.BUY)
                .setActualPrice(1.0)
                .setPrice(1.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelTruthy() {
        val transactionSettingsDto = TransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TransactionDto.newBuilder()
                .setId("1234")
                .setAccountId("123456789012345678901234567890123456")
                .setDate(OffsetDateTime.now().toString())
                .setSymbol("XYZ")
                .setType(TransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = ShareJournalDto.newBuilder()
                .setAction(ShareJournalDto.ActionType.BUY)
                .setActualPrice(1.0)
                .setPrice(1.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertTrue(RequestValidator.validateShareJournalModel(shareJournalDto))
    }
}