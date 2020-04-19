package com.example.tradelog.api.core.service

import com.example.tradelog.api.core.model.PortfolioModel
import com.example.tradelog.api.db.repository.PortfolioRepository
import org.springframework.stereotype.Service

@Service
class PortfolioService(private val repository: PortfolioRepository) {

    fun getPortfolios(accountId: String): List<PortfolioModel> {
        return repository.getPortfolios(accountId)
    }

    fun getDefaultPortfolio(accountId: String): PortfolioModel? {
        return repository.getDefaultPortfolio(accountId)
    }
}