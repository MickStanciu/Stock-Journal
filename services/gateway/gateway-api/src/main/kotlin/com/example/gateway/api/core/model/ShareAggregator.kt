package com.example.gateway.api.core.model

class ShareAggregator(val symbol: String,
                      var averageBoughtPrice: Double = 0.00,
                      val actualPrice: Double = 0.00,
                      val preferredPrice: Double = 0.00,
                      var outstandingShares: Int = 0) {

    private var totalPL: Double = 0.00

    fun addQuantityAndPrice(quantity: Int, price: Double) {
        this.totalPL += quantity * price
        this.outstandingShares += quantity
        if (this.outstandingShares != 0) {
            this.averageBoughtPrice = this.totalPL / this.outstandingShares
        }
    }
}
