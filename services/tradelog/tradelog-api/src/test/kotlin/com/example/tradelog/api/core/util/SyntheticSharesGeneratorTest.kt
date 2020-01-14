package com.example.tradelog.api.core.util

import com.example.tradelog.api.core.model.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class SyntheticSharesGeneratorTest {

    private var shareList: ArrayList<ShareJournalModel> = ArrayList()

    @BeforeEach
    fun init() {
        this.shareList = ArrayList()
    }


    @Test
    fun testWhereThereIsNoNeedToGenerate() {
        val settingsModel = TransactionSettingsModel(transactionId = "1234", preferredPrice = 0.0, groupSelected = false, legClosed = false)
        val transactionModel = TransactionModel(id = "1234", accountId = "123", date = OffsetDateTime.now(), symbol = "XYZ",
                type = TransactionType.SHARE, brokerFees = 0.0, settings = settingsModel)

        shareList.add(ShareJournalModel(transactionDetails = transactionModel, price = 10.0, actualPrice = 10.0, quantity = 500, action = ActionType.BUY))
        shareList.add(ShareJournalModel(transactionDetails = transactionModel, price = 10.0, actualPrice = 60.0, quantity = 500, action = ActionType.SELL))

        val syn = createSynthetic(shareList)

        Assertions.assertEquals(0, syn.size)
    }


    @Test
    fun testAveragePrice_1() {
        val settingsModel = TransactionSettingsModel(transactionId = "1234", preferredPrice = 0.0, groupSelected = false, legClosed = false)
        val transactionModel = TransactionModel(id = "1234", accountId = "123", date = OffsetDateTime.now(), symbol = "XYZ",
                type = TransactionType.SHARE, brokerFees = 0.0, settings = settingsModel)

        shareList.add(ShareJournalModel(transactionDetails = transactionModel, price = 55.0, actualPrice = 60.0, quantity = 100, action = ActionType.BUY))
        shareList.add(ShareJournalModel(transactionDetails = transactionModel, price = 66.0, actualPrice = 60.0, quantity = 200, action = ActionType.BUY))

        val syn = createSynthetic(shareList)

        Assertions.assertEquals(1, syn.size)

        val xyz = syn[0]
        Assertions.assertEquals(ActionType.SELL, xyz.action)
        Assertions.assertEquals(300, xyz.quantity)
        Assertions.assertEquals(62.333333333333336, xyz.price)
        Assertions.assertEquals(60.0, xyz.actualPrice)
        Assertions.assertEquals(0.00, xyz.transactionDetails.settings.preferredPrice)
    }


    @Test
    fun testAveragePrice_2() {
        val settingsModel1 = TransactionSettingsModel(transactionId = "1234", preferredPrice = 99.0, groupSelected = false, legClosed = false)
        val transactionModel1 = TransactionModel(id = "1234", accountId = "123", date = OffsetDateTime.now(), symbol = "XYZ",
                type = TransactionType.SHARE, brokerFees = 0.0, settings = settingsModel1)

        val settingsModel2 = TransactionSettingsModel(transactionId = "1234", preferredPrice = 99.0, groupSelected = false, legClosed = true)
        val transactionModel2 = TransactionModel(id = "1234", accountId = "123", date = OffsetDateTime.now(), symbol = "XYZ",
                type = TransactionType.SHARE, brokerFees = 0.0, settings = settingsModel2)

        shareList.add(ShareJournalModel(transactionDetails = transactionModel1, price = 55.0, actualPrice = 60.0, quantity = 100, action = ActionType.BUY))
        shareList.add(ShareJournalModel(transactionDetails = transactionModel1, price = 45.0, actualPrice = 60.0, quantity = 50, action = ActionType.BUY))
        shareList.add(ShareJournalModel(transactionDetails = transactionModel2, price = 80.0, actualPrice = 60.0, quantity = 550, action = ActionType.BUY))

        val syn = createSynthetic(shareList)

        Assertions.assertEquals(1, syn.size)

        val xyz = syn[0]
        Assertions.assertEquals(ActionType.SELL, xyz.action)
        Assertions.assertEquals(150, xyz.quantity)
        Assertions.assertEquals(51.666666666666664, xyz.price)
        Assertions.assertEquals(60.0, xyz.actualPrice)
        Assertions.assertEquals(99.00, xyz.transactionDetails.settings.preferredPrice)
    }


    @Test
    fun testSynCSCO100AndADBE200() {
        val settingsModel = TransactionSettingsModel(transactionId = "1234", preferredPrice = 44.0, groupSelected = false, legClosed = false)
        val transactionModel1 = TransactionModel(id = "1234", accountId = "123", date = OffsetDateTime.now(), symbol = "CSCO",
                type = TransactionType.SHARE, brokerFees = 0.0, settings = settingsModel)
        val transactionModel2 = TransactionModel(id = "1234", accountId = "123", date = OffsetDateTime.now(), symbol = "ADBE",
                type = TransactionType.SHARE, brokerFees = 0.0, settings = settingsModel)

        shareList.add(ShareJournalModel(transactionDetails = transactionModel1, price = 10.0, actualPrice = 60.0, quantity = 500, action = ActionType.BUY))
        shareList.add(ShareJournalModel(transactionDetails = transactionModel2, price = 10.0, actualPrice = 60.0, quantity = 500, action = ActionType.BUY))
        shareList.add(ShareJournalModel(transactionDetails = transactionModel1, price = 10.0, actualPrice = 60.0, quantity = 400, action = ActionType.SELL))
        shareList.add(ShareJournalModel(transactionDetails = transactionModel2, price = 10.0, actualPrice = 60.0, quantity = 300, action = ActionType.SELL))

        val syn = createSynthetic(shareList)

        Assertions.assertEquals(2, syn.size)

        syn.stream()
                .filter { it.transactionDetails.symbol == "CSCO" }
                .forEach {
                    Assertions.assertEquals(ActionType.SELL, it.action)
                    Assertions.assertEquals(100, it.quantity)
                }

        syn.stream()
                .filter { it.transactionDetails.symbol == "ADBE" }
                .forEach {
                    Assertions.assertEquals(ActionType.SELL, it.action)
                    Assertions.assertEquals(200, it.quantity)
                }
    }

    @Test
    fun testBuySellKO() {
        val settingsModel = TransactionSettingsModel(transactionId = "4cd7a362-427a-477f-b995-de525281c957", preferredPrice = 0.0, groupSelected = false, legClosed = true)
        val transactionModel = TransactionModel(id = "4cd7a362-427a-477f-b995-de525281c957", accountId = "123", date = OffsetDateTime.now(), symbol = "KO",
                type = TransactionType.SHARE, brokerFees = 0.0, settings = settingsModel)
        shareList.add(ShareJournalModel(transactionDetails = transactionModel, price = 55.77, actualPrice = 0.0, quantity = 100, action = ActionType.BUY))
        shareList.add(ShareJournalModel(transactionDetails = transactionModel, price = 56.00, actualPrice = 0.0, quantity = 100, action = ActionType.SELL))

        val syn = createSynthetic(shareList)
        Assertions.assertEquals(0, syn.size)
    }


    @Test
    fun testSingleBuyTrade() {
        val settingsModel = TransactionSettingsModel(transactionId = "1234", preferredPrice = 44.0, groupSelected = false, legClosed = false)
        val transactionModel = TransactionModel(id = "1234", accountId = "123", date = OffsetDateTime.now(), symbol = "XYZ",
                type = TransactionType.SHARE, brokerFees = 0.0, settings = settingsModel)

        shareList.add(ShareJournalModel(transactionDetails = transactionModel, price = 10.0, actualPrice = 10.0, quantity = 100, action = ActionType.BUY))

        val syn = createSynthetic(shareList)

        Assertions.assertEquals(1, syn.size)

        val xyz = syn[0]
        Assertions.assertEquals(ActionType.SELL, xyz.action)
        Assertions.assertEquals(100, xyz.quantity)
        Assertions.assertEquals(TransactionType.SYNTHETIC_SHARE, xyz.transactionDetails.type)
        Assertions.assertEquals("XYZ", xyz.transactionDetails.symbol)
    }

}