package com.example.gateway.api.rest.converter

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CsvProcessorTest {
    private val csvResponse = """
        symbol,open,high,low,price,volume,latestDay,previousClose,change,changePercent
        MSFT,167.5100,167.5300,164.4500,165.0400,24918117,2020-01-24,166.7200,-1.6800,-1.0077%
    """.trimIndent()

    @Test
    fun testConvert() {
        val bits = CsvProcessor.getCsvBits(csvResponse)
        val previousClose: Double = bits["previousClose"]!!.toDouble()
        Assertions.assertEquals(previousClose, 166.7200)
    }
}