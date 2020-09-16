package com.example.tradelog.api.rest.validator

import com.example.tradelog.api.spec.model.TLDividendJournalDto
import com.example.tradelog.api.spec.model.TLTransactionDto
import com.example.tradelog.api.spec.model.TLTransactionSettingsDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class ValidateDividendJournalModelTest {

    @Test fun plm() {

        val text = """
            >>>>>>>>> 
            SELECT p.*
            FROM fts_properties p
            WHERE p.org_name = :orgName
              AND p.address ILIKE :fullTextSearchQueryString
              AND (p.agent_id IN (:agentUserIds) OR p.secondary_agent_id IN (:agentUserIds) OR p.sales_assistant_id IN (:agentUserIds))
              AND 1 = 1
              AND 1 = 1
              AND 1 = 1
              AND 1 = 1
              AND 1 = 1
              
              LIMIT 20
            >>>>>>>>> 
        """.trimIndent()

        println(text)
        println(text.replace("  \n", ""))
    }

    @Test
    fun testValidateDividendJournalModelWhenDividendIsNegative() {
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
                .setType(TLTransactionDto.TransactionType.DIVIDEND)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val dividendJournalDto = TLDividendJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setQuantity(10)
                .setDividend(-1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateDividendJournalModel(dividendJournalDto))
    }


    @Test
    fun testValidateDividendJournalModelWhenQuantityIsNegative() {
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
                .setType(TLTransactionDto.TransactionType.DIVIDEND)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val dividendJournalDto = TLDividendJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setQuantity(-10)
                .setDividend(1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateDividendJournalModel(dividendJournalDto))
    }


    @Test
    fun testValidateDividendJournalModelWhenTransactionTypeIsNotDividend() {
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

        val dividendJournalDto = TLDividendJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setQuantity(10)
                .setDividend(1.0)
                .build()

        Assertions.assertFalse(RequestValidator.validateDividendJournalModel(dividendJournalDto))
    }


    @Test
    fun testValidateDividendJournalModelTruthy() {
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
                .setType(TLTransactionDto.TransactionType.DIVIDEND)
                .setBrokerFees(0.00)
                .setSettings(transactionSettingsDto)
                .build()

        val dividendJournalDto = TLDividendJournalDto.newBuilder()
                .setTransactionDetails(transactionDto)
                .setQuantity(10)
                .setDividend(1.0)
                .build()

        Assertions.assertTrue(RequestValidator.validateDividendJournalModel(dividendJournalDto))
    }
}