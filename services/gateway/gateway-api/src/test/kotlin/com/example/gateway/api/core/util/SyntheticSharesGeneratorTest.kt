package com.example.gateway.api.core.util

import com.example.gateway.api.core.model.ActionType
import com.example.gateway.api.core.model.ShareJournalModel
import com.example.gateway.api.core.model.TransactionType
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
        val shareModel1 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "XYZ",
                date = OffsetDateTime.now(),
                price = 10.0,
                preferredPrice = 0.0,
                actualPrice = 10.0,
                quantity = 500,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = false,
                transactionType = TransactionType.SHARE,
                action = ActionType.BUY
        )

        val shareModel2 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "XYZ",
                date = OffsetDateTime.now(),
                price = 10.0,
                preferredPrice = 0.0,
                actualPrice = 60.0,
                quantity = 500,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = false,
                transactionType = TransactionType.SHARE,
                action = ActionType.SELL
        )

        shareList.add(shareModel1)
        shareList.add(shareModel2)

        val syn = createSynthetic(shareList)

        Assertions.assertEquals(0, syn.size)
    }


    @Test
    fun testAveragePrice_1() {
        val shareModel1 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "XYZ",
                date = OffsetDateTime.now(),
                price = 55.0,
                preferredPrice = 0.0,
                actualPrice = 60.0,
                quantity = 100,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = false,
                transactionType = TransactionType.SHARE,
                action = ActionType.BUY
        )

        val shareModel2 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "XYZ",
                date = OffsetDateTime.now(),
                price = 66.0,
                preferredPrice = 0.0,
                actualPrice = 60.0,
                quantity = 200,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = false,
                transactionType = TransactionType.SHARE,
                action = ActionType.BUY
        )

        shareList.add(shareModel1)
        shareList.add(shareModel2)

        val syn = createSynthetic(shareList)

        Assertions.assertEquals(1, syn.size)

        val xyz = syn[0]
        Assertions.assertEquals(ActionType.SELL, xyz.action)
        Assertions.assertEquals(300, xyz.quantity)
        Assertions.assertEquals(62.333333333333336, xyz.price)
        Assertions.assertEquals(60.0, xyz.actualPrice)
        Assertions.assertEquals(0.00, xyz.preferredPrice)
    }


    @Test
    fun testAveragePrice_2() {
        val shareModel1 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "XYZ",
                date = OffsetDateTime.now(),
                price = 55.0,
                preferredPrice = 99.0,
                actualPrice = 60.0,
                quantity = 100,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = false,
                transactionType = TransactionType.SHARE,
                action = ActionType.BUY
        )

        val shareModel2 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "XYZ",
                date = OffsetDateTime.now(),
                price = 45.0,
                preferredPrice = 99.0,
                actualPrice = 60.0,
                quantity = 50,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = false,
                transactionType = TransactionType.SHARE,
                action = ActionType.BUY
        )

        val shareModel3 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "XYZ",
                date = OffsetDateTime.now(),
                price = 80.0,
                preferredPrice = 99.0,
                actualPrice = 60.0,
                quantity = 550,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = true,
                transactionType = TransactionType.SHARE,
                action = ActionType.BUY
        )


        shareList.add(shareModel1)
        shareList.add(shareModel2)
        shareList.add(shareModel3)

        val syn = createSynthetic(shareList)

        Assertions.assertEquals(1, syn.size)

        val xyz = syn[0]
        Assertions.assertEquals(ActionType.SELL, xyz.action)
        Assertions.assertEquals(700, xyz.quantity)
        Assertions.assertEquals(73.92857142857143, xyz.price)
        Assertions.assertEquals(60.0, xyz.actualPrice)
        Assertions.assertEquals(99.00, xyz.preferredPrice)
    }


    @Test
    fun testSynCSCO100AndADBE200() {
        val csco1 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "CSCO",
                date = OffsetDateTime.now(),
                price = 10.0,
                preferredPrice = 44.0,
                actualPrice = 60.0,
                quantity = 500,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = false,
                transactionType = TransactionType.SHARE,
                action = ActionType.BUY
        )

        val csco2 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "CSCO",
                date = OffsetDateTime.now(),
                price = 10.0,
                preferredPrice = 44.0,
                actualPrice = 60.0,
                quantity = 400,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = false,
                transactionType = TransactionType.SHARE,
                action = ActionType.SELL
        )

        val adbe1 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "ADBE",
                date = OffsetDateTime.now(),
                price = 10.0,
                preferredPrice = 44.0,
                actualPrice = 60.0,
                quantity = 500,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = false,
                transactionType = TransactionType.SHARE,
                action = ActionType.BUY
        )

        val adbe2 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "ADBE",
                date = OffsetDateTime.now(),
                price = 10.0,
                preferredPrice = 44.0,
                actualPrice = 60.0,
                quantity = 300,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = false,
                transactionType = TransactionType.SHARE,
                action = ActionType.SELL
        )

        shareList.add(csco1)
        shareList.add(adbe1)
        shareList.add(csco2)
        shareList.add(adbe2)

        val syn = createSynthetic(shareList)

        Assertions.assertEquals(2, syn.size)

        syn.stream()
                .filter { it.symbol == "CSCO" }
                .forEach {
                    Assertions.assertEquals(ActionType.SELL, it.action)
                    Assertions.assertEquals(100, it.quantity)
                }

        syn.stream()
                .filter { it.symbol == "ADBE" }
                .forEach {
                    Assertions.assertEquals(ActionType.SELL, it.action)
                    Assertions.assertEquals(200, it.quantity)
                }
    }

    @Test
    fun testBuySellKO() {
        val shareModel1 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "XYZ",
                date = OffsetDateTime.now(),
                price = 55.77,
                preferredPrice = 0.0,
                actualPrice = 0.0,
                quantity = 100,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = true,
                transactionType = TransactionType.SHARE,
                action = ActionType.BUY
        )

        val shareModel2 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "XYZ",
                date = OffsetDateTime.now(),
                price = 56.00,
                preferredPrice = 0.0,
                actualPrice = 0.0,
                quantity = 100,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = true,
                transactionType = TransactionType.SHARE,
                action = ActionType.SELL
        )

        shareList.add(shareModel1)
        shareList.add(shareModel2)

        val syn = createSynthetic(shareList)
        Assertions.assertEquals(0, syn.size)
    }


    @Test
    fun testSingleBuyTrade() {
        val shareModel1 = ShareJournalModel(
                transactionId = "1234",
                accountId = "123",
                symbol = "XYZ",
                date = OffsetDateTime.now(),
                price = 10.0,
                preferredPrice = 44.0,
                actualPrice = 10.0,
                quantity = 100,
                brokerFees = 0.0,
                groupSelected = false,
                legClosed = false,
                transactionType = TransactionType.SHARE,
                action = ActionType.BUY
        )

        shareList.add(shareModel1)

        val syn = createSynthetic(shareList)

        Assertions.assertEquals(1, syn.size)

        val xyz = syn[0]
        Assertions.assertEquals(ActionType.SELL, xyz.action)
        Assertions.assertEquals(100, xyz.quantity)
        Assertions.assertEquals(TransactionType.SYNTHETIC_SHARE, xyz.transactionType)
        Assertions.assertEquals("XYZ", xyz.symbol)
    }

}