package com.example.gateway.api.spec

class StatisticsGWModel(

        var totalCalls: Int,
        var selectedCalls: Int,
        var totalPuts: Int,
        var selectedPuts: Int,
        var totalStocks: Int,
        var selectedStocks: Int,
        var totalRealisedPremium: Double,
        var selectedRealisedPremium: Double
)