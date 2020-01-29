package com.example.gateway.api.scheduler

import com.example.gateway.api.core.service.StockDataService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class Scheduler(private val stockDataService: StockDataService) {

    companion object {
        private val LOG = LoggerFactory.getLogger(Scheduler::class.java)
    }

    @Scheduled(cron = "0 * 9 * * ?")
    fun bla() {
        val symbols = stockDataService.getSymbolsForUpdate();
        LOG.info("CRON TRIGGERED")
    }
}
