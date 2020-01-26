package com.example.tradelog.api.rest.validator

import com.example.tradelog.api.spec.model.TLShareJournalDto
import com.example.tradelog.api.spec.model.TLTransactionDto
import com.example.tradelog.api.spec.model.TLTransactionSettingsDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class ValidateShareJournalModelTest {

    @Test
    fun testValidateShareJournalModelWhenActionIsInvalid() {
        val transactionSettingsDto = TLTransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TLTransactionDto.newBuilder()
                .setId("1234")
                .setAccountId("123")
                .setDate(OffsetDateTime.now().toString())
                .setAccountId("123456789012345678901234567890123456")
                .setSymbol("XYZ")
                .setType(TLTransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = TLShareJournalDto.newBuilder()
                .setAction(TLShareJournalDto.ActionType.UNKNOWN_ACTION_TYPE)
                .setPrice(1.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelWhenAccountIsEmpty() {
        val transactionSettingsDto = TLTransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TLTransactionDto.newBuilder()
                .setId("1234")
                .setDate(OffsetDateTime.now().toString())
                .setSymbol("XYZ")
                .setType(TLTransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = TLShareJournalDto.newBuilder()
                .setAction(TLShareJournalDto.ActionType.BUY)
                .setPrice(1.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelWhenPriceIsNegative() {
        val transactionSettingsDto = TLTransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TLTransactionDto.newBuilder()
                .setId("1234")
                .setDate(OffsetDateTime.now().toString())
                .setAccountId("123456789012345678901234567890123456")
                .setSymbol("XYZ")
                .setType(TLTransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = TLShareJournalDto.newBuilder()
                .setAction(TLShareJournalDto.ActionType.BUY)
                .setPrice(-11.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelWhenQuantityIsZero() {
        val transactionSettingsDto = TLTransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TLTransactionDto.newBuilder()
                .setId("1234")
                .setDate(OffsetDateTime.now().toString())
                .setAccountId("123456789012345678901234567890123456")
                .setSymbol("XYZ")
                .setType(TLTransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = TLShareJournalDto.newBuilder()
                .setAction(TLShareJournalDto.ActionType.BUY)
                .setPrice(1.0)
                .setQuantity(-1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelWhenSymbolIsEmpty() {
        val transactionSettingsDto = TLTransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TLTransactionDto.newBuilder()
                .setId("1234")
                .setDate(OffsetDateTime.now().toString())
                .setAccountId("123456789012345678901234567890123456")
                .setSymbol("")
                .setType(TLTransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = TLShareJournalDto.newBuilder()
                .setAction(TLShareJournalDto.ActionType.BUY)
                .setPrice(1.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelWhenSymbolIsSpace() {
        val transactionSettingsDto = TLTransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TLTransactionDto.newBuilder()
                .setId("1234")
                .setDate(OffsetDateTime.now().toString())
                .setAccountId("123456789012345678901234567890123456")
                .setSymbol(" ")
                .setType(TLTransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = TLShareJournalDto.newBuilder()
                .setAction(TLShareJournalDto.ActionType.BUY)
                .setPrice(1.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelWhenTransactionTypeIsNotStock() {
        val transactionSettingsDto = TLTransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TLTransactionDto.newBuilder()
                .setId("1234")
                .setDate(OffsetDateTime.now().toString())
                .setAccountId("123456789012345678901234567890123456")
                .setSymbol("XYZ")
                .setType(TLTransactionDto.TransactionType.OPTION)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = TLShareJournalDto.newBuilder()
                .setAction(TLShareJournalDto.ActionType.BUY)
                .setPrice(1.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertFalse(RequestValidator.validateShareJournalModel(shareJournalDto))
    }


    @Test
    fun testValidateShareJournalModelTruthy() {
        val transactionSettingsDto = TLTransactionSettingsDto.newBuilder()
                .setTransactionId("1234")
                .setPreferredPrice(0.00)
                .setGroupSelected(false)
                .setLegClosed(false)
                .build()

        val transactionDto = TLTransactionDto.newBuilder()
                .setId("1234")
                .setAccountId("123456789012345678901234567890123456")
                .setDate(OffsetDateTime.now().toString())
                .setSymbol("XYZ")
                .setType(TLTransactionDto.TransactionType.SHARE)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val shareJournalDto = TLShareJournalDto.newBuilder()
                .setAction(TLShareJournalDto.ActionType.BUY)
                .setPrice(1.0)
                .setQuantity(1)
                .setTransactionDetails(transactionDto)
                .build()

        Assertions.assertTrue(RequestValidator.validateShareJournalModel(shareJournalDto))
    }
}