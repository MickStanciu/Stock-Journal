package com.example.gateway.api.scheduler

import arrow.core.getOrHandle
import com.example.gateway.api.core.service.StockDataService
import com.example.gateway.api.core.service.TransactionService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class Scheduler(
        private val stockDataService: StockDataService,
        private val transactionService: TransactionService) {

    companion object {
        private val LOG = LoggerFactory.getLogger(Scheduler::class.java)
    }

    //EVERY HOUR
    @Scheduled(cron = "0 0 * * * ?")
    fun updateActiveSymbols() {
        val symbols = transactionService.getActiveSymbols().getOrHandle { throw it }
        if (symbols.isNotEmpty()) {
            stockDataService.updateSymbols(symbols)
        }
    }

    //EVERY MINUTE
    @Scheduled(cron = "0 * * * * ?")
    fun updatePrices() {
        val priceModels = stockDataService.getSymbolsForUpdate()
        priceModels.forEach {
            if ("XYZ" != it.symbol) {
                LOG.info("Processing update price for ${it.symbol}")
                stockDataService.updatePrice(it)
        }}
    }
}
