package com.example.stockdata.api.core.service

import arrow.core.Either
import com.example.common.converter.TimeConverter
import com.example.common.exception.ApiException
import com.example.stockdata.api.core.model.PriceModel
import com.example.stockdata.api.db.repository.PriceRepository
import org.springframework.stereotype.Service

@Service
class PriceService(private val priceRepository: PriceRepository) {

    fun getPrice(symbol: String): Either<ApiException, PriceModel> = priceRepository.getBySymbol(symbol)

    fun updatePrice(priceModel: PriceModel): Either<ApiException, Unit> = priceRepository.updateForSymbol(priceModel)

    fun getNotUpdatedSymbols(adjustedLimit: Int): Either<ApiException, List<PriceModel>> = priceRepository.getNotUpdatedSymbols(adjustedLimit)

    fun updateSymbols(symbolsList: List<String>): Either<ApiException, Unit> {
        //NOTE: not optimal, use batch next time
        var exception: ApiException? = null
        symbolsList.forEach {
            val priceModel = PriceModel(
                    symbol = it,
                    lastClose = 0.00,
                    lastUpdatedOn = TimeConverter.getOffsetDateTimeNow(),
                    lastFailedOn = null,
                    active = true
            )
            val response = priceRepository.addSymbol(priceModel)
            if (response.isLeft() && exception == null) exception = response.fold({ ex -> ex }, { null })
        }
        return exception?.let { Either.Left(it) } ?: Either.Right(Unit)
    }
}
