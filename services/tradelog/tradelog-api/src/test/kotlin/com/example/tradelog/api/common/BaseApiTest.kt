//package com.example.tradelog.api.common
//
//import com.example.common.converter.TimeConverter
//import com.example.tradelog.api.core.model.TransactionModel
//import com.example.tradelog.api.core.model.TransactionSettingsModel
//import com.example.tradelog.api.core.model.TransactionType
//import com.example.tradelog.api.db.repository.TransactionRepository
//import org.junit.jupiter.api.BeforeAll
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import java.util.*
//
////@SpringBootTest
////@ExtendWith(SpringExtension::class)
//class BaseApiTest() {
//
//    @MockBean
//    private lateinit var transactionRepository: TransactionRepository
//
//    @BeforeEach
//    fun init() {
//        println("BEFORE ALL")
//    }
//
//    @Test
//    fun tryme() {
//        //WORK IN PROGRESS> NEED DOCKER CONTAINER
////        val id = createTransactionRecord()
////        println(id)
//
//    }
//
//    protected fun createTransactionRecord(symbol: String = "XYZ", type: TransactionType = TransactionType.SHARE, brokerFees: Double = 0.00): String {
//        val transactionId = UUID.randomUUID().toString()
//        val settings = TransactionSettingsModel(transactionId = transactionId, preferredPrice = 0.00, groupSelected = false, legClosed = false)
//        val model = TransactionModel(id = transactionId, accountId = UUID.randomUUID().toString(), portfolioId = UUID.randomUUID().toString(),
//                date = TimeConverter.getOffsetDateTimeNow(), symbol = symbol, type = type, brokerFees = brokerFees, settings = settings)
//
//        val maybeId = transactionRepository.createRecord(model)
//        maybeId shouldNotBe null
//        return maybeId!!
//    }
//}