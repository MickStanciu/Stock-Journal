package com.example.tradelog.api.rest.validator

import com.example.tradelog.api.spec.model.ActionType
import com.example.tradelog.api.spec.model.OptionJournalDto
import com.example.tradelog.api.spec.model.TransactionDto
import com.example.tradelog.api.spec.model.TransactionSettingsDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class ValidateOptionJournalModelTest {

    @Test
    fun testValidateOptionJournalModelWhenActionIsInvalid() {
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

        val optionJournalDto = OptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(OptionJournalDto.OptionType.CALL)
                .setAction(ActionType.UNKNOWN)
                .setContracts(1)
                .setExpiryDate(OffsetDateTime.now().toString())
                .setPremium(1.0)
                .setStockPrice(1.0)
                .setStrikePrice(1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateOptionJournalModel(optionJournalDto))
    }


    @Test
    fun testValidateOptionJournalModelWhenOptionTypeIsUnknown() {
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

        val optionJournalDto = OptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(OptionJournalDto.OptionType.UNKNOWN)
                .setAction(ActionType.SELL)
                .setContracts(1)
                .setExpiryDate(OffsetDateTime.now().toString())
                .setPremium(1.0)
                .setStockPrice(1.0)
                .setStrikePrice(1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateOptionJournalModel(optionJournalDto))
    }


    @Test
    fun testValidateOptionJournalModelWhenStockPriceIsNegative() {
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

        val optionJournalDto = OptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(OptionJournalDto.OptionType.CALL)
                .setAction(ActionType.SELL)
                .setContracts(1)
                .setExpiryDate(OffsetDateTime.now().toString())
                .setPremium(1.0)
                .setStockPrice(-1.0)
                .setStrikePrice(1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateOptionJournalModel(optionJournalDto))
    }


    @Test
    fun testValidateOptionJournalModelWhenContractsIsZero() {
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

        val optionJournalDto = OptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(OptionJournalDto.OptionType.CALL)
                .setAction(ActionType.SELL)
                .setContracts(0)
                .setExpiryDate(OffsetDateTime.now().toString())
                .setPremium(1.0)
                .setStockPrice(1.0)
                .setStrikePrice(1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateOptionJournalModel(optionJournalDto))
    }


    @Test
    fun testValidateOptionJournalModelWhenSymbolIsEmpty() {
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
                .setType(TransactionDto.TransactionType.OPTION)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val optionJournalDto = OptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(OptionJournalDto.OptionType.CALL)
                .setAction(ActionType.SELL)
                .setContracts(1)
                .setExpiryDate(OffsetDateTime.now().toString())
                .setPremium(1.0)
                .setStockPrice(1.0)
                .setStrikePrice(1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateOptionJournalModel(optionJournalDto))
    }


    @Test
    fun testValidateOptionJournalModelWhenSymbolIsSpace() {
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
                .setType(TransactionDto.TransactionType.OPTION)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val optionJournalDto = OptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(OptionJournalDto.OptionType.CALL)
                .setAction(ActionType.SELL)
                .setContracts(1)
                .setExpiryDate(OffsetDateTime.now().toString())
                .setPremium(1.0)
                .setStockPrice(1.0)
                .setStrikePrice(1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateOptionJournalModel(optionJournalDto))
    }


    @Test
    fun testValidateOptionJournalModelWhenStrikePriceIsNegative() {
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

        val optionJournalDto = OptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(OptionJournalDto.OptionType.CALL)
                .setAction(ActionType.SELL)
                .setContracts(1)
                .setExpiryDate(OffsetDateTime.now().toString())
                .setPremium(1.0)
                .setStockPrice(1.0)
                .setStrikePrice(-1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateOptionJournalModel(optionJournalDto))
    }


    @Test
    fun testValidateOptionJournalModelTruthy() {
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

        val optionJournalDto = OptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(OptionJournalDto.OptionType.CALL)
                .setAction(ActionType.SELL)
                .setContracts(1)
                .setExpiryDate(OffsetDateTime.now().toString())
                .setPremium(1.0)
                .setStockPrice(1.0)
                .setStrikePrice(1.0)
                .build()

        Assertions.assertTrue(RequestValidator.validateOptionJournalModel(optionJournalDto))
    }
}