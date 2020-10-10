package com.example.stockdata.api.core.service

import arrow.core.Either
import com.example.common.converter.TimeConverter
import com.example.common.exception.ApiException
import com.example.stockdata.api.core.model.PriceModel
import com.example.stockdata.api.db.repository.PriceRepository
import org.springframework.stereotype.Service

@Service
class PriceService(private val priceRepository: PriceRepository) {

    fun getPrice(symbol: String): Either<ApiException, PriceModel> {
        return priceRepository.getBySymbol(symbol)
    }

    fun updatePrice(priceModel: PriceModel) {
        priceRepository.updateForSymbol(priceModel)
    }

    fun getNotUpdatedSymbols(adjustedLimit: Int): List<PriceModel> {
        return priceRepository.getNotUpdatedSymbols(adjustedLimit)
    }

    fun updateSymbols(symbolsList: List<String>) {
        //NOTE: not optimal
        symbolsList.forEach {
            val priceModel = PriceModel(
                    symbol = it,
                    lastClose = 0.00,
                    lastUpdatedOn = TimeConverter.getOffsetDateTimeNow(),
                    lastFailedOn = null,
                    active = true
            )
            priceRepository.addSymbol(priceModel)
        }
    }
}
