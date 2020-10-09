package com.example.gateway.api.rest.converter

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class AlphaVantageCsvConverterTest {

    private val csvResponse = """
        symbol,open,high,low,price,volume,latestDay,previousClose,change,changePercent
        MSFT,167.5100,167.5300,164.4500,165.0400,24918117,2020-01-24,166.7200,-1.6800,-1.0077%
    """.trimIndent()

    @Test
    fun testConvert() {
        val response = AlphaVantageCsvConverter.toModel(csvResponse)
        response shouldNotBe null
        response?.lastClose shouldBe 166.7200
    }

}