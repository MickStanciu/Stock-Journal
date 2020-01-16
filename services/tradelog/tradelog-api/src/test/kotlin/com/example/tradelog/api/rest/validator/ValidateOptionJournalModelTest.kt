package com.example.tradelog.api.rest.validator

import com.example.tradelog.api.spec.model.TLOptionJournalDto
import com.example.tradelog.api.spec.model.TLTransactionDto
import com.example.tradelog.api.spec.model.TLTransactionSettingsDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class ValidateOptionJournalModelTest {

    @Test
    fun testValidateOptionJournalModelWhenActionIsInvalid() {
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

        val optionJournalDto = TLOptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(TLOptionJournalDto.OptionType.CALL)
                .setAction(TLOptionJournalDto.ActionType.UNKNOWN_ACTION_TYPE)
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

        val optionJournalDto = TLOptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(TLOptionJournalDto.OptionType.UNKNOWN_OPTION_TYPE)
                .setAction(TLOptionJournalDto.ActionType.SELL)
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

        val optionJournalDto = TLOptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(TLOptionJournalDto.OptionType.CALL)
                .setAction(TLOptionJournalDto.ActionType.SELL)
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

        val optionJournalDto = TLOptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(TLOptionJournalDto.OptionType.CALL)
                .setAction(TLOptionJournalDto.ActionType.SELL)
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
                .setType(TLTransactionDto.TransactionType.OPTION)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val optionJournalDto = TLOptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(TLOptionJournalDto.OptionType.CALL)
                .setAction(TLOptionJournalDto.ActionType.SELL)
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
                .setType(TLTransactionDto.TransactionType.OPTION)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val optionJournalDto = TLOptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(TLOptionJournalDto.OptionType.CALL)
                .setAction(TLOptionJournalDto.ActionType.SELL)
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

        val optionJournalDto = TLOptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(TLOptionJournalDto.OptionType.CALL)
                .setAction(TLOptionJournalDto.ActionType.SELL)
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

        val optionJournalDto = TLOptionJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setOptionType(TLOptionJournalDto.OptionType.CALL)
                .setAction(TLOptionJournalDto.ActionType.SELL)
                .setContracts(1)
                .setExpiryDate(OffsetDateTime.now().toString())
                .setPremium(1.0)
                .setStockPrice(1.0)
                .setStrikePrice(1.0)
                .build()

        Assertions.assertTrue(RequestValidator.validateOptionJournalModel(optionJournalDto))
    }
}