package com.example.gateway.api.core.model

class ShareAggregator(val symbol: String,
                      var averageBoughtPrice: Double = 0.00,
                      val actualPrice: Double = 0.00,
                      val preferredPrice: Double = 0.00,
                      var quantity: Int = 0) {

    fun addQuantityAndPrice(quantity: Int, price: Double) {
        if (averageBoughtPrice == 0.00 && this.quantity == 0) {
            this.quantity = quantity
            this.averageBoughtPrice = price
        } else {
            var tempValue: Double = this.quantity * this.averageBoughtPrice
            tempValue += quantity * price
            this.quantity += quantity
            this.averageBoughtPrice = tempValue / this.quantity
        }
    }
}
