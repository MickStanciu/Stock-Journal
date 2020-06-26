package com.example.tradelog.api.core.service

import com.example.common.service.ServiceError
import com.example.common.service.toServiceError
import com.example.common.types.Either
import com.example.tradelog.api.core.model.PortfolioModel
import com.example.tradelog.api.db.repository.PortfolioRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PortfolioService(private val repository: PortfolioRepository) {

    fun getPortfolios(accountId: UUID): Either<ServiceError, List<PortfolioModel>> {
        return repository.getPortfolios(accountId)
                .mapLeft(::toServiceError)
    }

    fun getDefaultPortfolio(accountId: UUID): Either<ServiceError, PortfolioModel> {
        return repository.getDefaultPortfolio(accountId)
                .mapLeft(::toServiceError)
    }
}