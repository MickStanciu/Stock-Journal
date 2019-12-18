package com.example.stockdata.api.core.service

import com.example.stockdata.api.core.model.PriceModel
import com.example.stockdata.api.db.PriceRepository
import org.springframework.stereotype.Service

@Service
class PriceService(val priceRepository: PriceRepository) {

    fun getPrice(symbol: String): PriceModel? {
        return priceRepository.getBySymbol(symbol)
    }

    fun updatePrice(priceModel: PriceModel) {
        priceRepository.updateForSymbol(priceModel)
    }

}
