package com.example.gateway.api.rest.converter

import arrow.core.getOrHandle
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class AlphaVantageCsvConverterTest {

    private val csvResponse = """
        symbol,open,high,low,price,volume,latestDay,previousClose,change,changePercent
        MSFT,167.5100,167.5300,164.4500,165.0400,24918117,2020-01-24,166.7200,-1.6800,-1.0077%
    """.trimIndent()

    @Test
    fun testConvert() {
        val response = AlphaVantageCsvConverter
                .toModel(csvResponse)
                .getOrHandle { fail(it) }

        response.lastClose shouldBe 166.7200
    }

}