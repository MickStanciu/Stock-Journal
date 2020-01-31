package com.example.stockdata.api.core.service

import com.example.stockdata.api.core.model.PriceModel
import com.example.stockdata.api.db.repository.PriceRepository
import org.springframework.stereotype.Service

@Service
class PriceService(private val priceRepository: PriceRepository) {

    fun getPrice(symbol: String): PriceModel? {
        return priceRepository.getBySymbol(symbol)
    }

    fun updatePrice(priceModel: PriceModel) {
        priceRepository.updateForSymbol(priceModel)
    }

    fun getNotUpdatedSymbols(adjustedLimit: Int): List<String> {
        return priceRepository.getNotUpdatedSymbols(adjustedLimit)
    }

    fun updateSymbols(symbolsList: List<String>) {
        priceRepository.updateSymbols(symbolsList)
    }
}
