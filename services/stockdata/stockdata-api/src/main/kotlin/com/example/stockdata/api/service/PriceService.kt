package com.example.stockdata.api.service

import com.example.stockdata.api.model.PriceModel
import com.example.stockdata.api.repository.PriceRepository
import org.springframework.stereotype.Service

@Service
class PriceService(val priceRepository: PriceRepository) {

    fun getPrice(symbol: String): PriceModel? {
        return priceRepository.getBySymbol(symbol)
    }

}
