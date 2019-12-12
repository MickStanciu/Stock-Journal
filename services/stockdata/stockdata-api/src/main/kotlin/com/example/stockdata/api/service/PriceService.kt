package com.example.stockdata.api.service

import com.example.stockdata.api.model.PriceModel
import org.springframework.stereotype.Service

@Service
class PriceService {
    fun getPrice(symbol: String): PriceModel {
        val priceModel = PriceModel(symbol, 10.5)
        return priceModel
    }
}