package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.SummaryMatrixModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SummaryMatrixConverterTest {

    @Test
    fun testConverter() {
        val models = ArrayList<SummaryMatrixModel>()
        models.add(SummaryMatrixModel(2000, 1, 100.00))
        models.add(SummaryMatrixModel(2000, 2, 50.00))
        models.add(SummaryMatrixModel(2000, 4, 25.00))
        models.add(SummaryMatrixModel(2000, 12, 1.00))
        models.add(SummaryMatrixModel(2001, 12, 1.00))

        val response = SummaryMatrixConverter.toGWDto(models)

        val year2000 = response[2000]
        Assertions.assertEquals(100.00, year2000?.monthsMap?.get(1))
        Assertions.assertEquals(50.00, year2000?.monthsMap?.get(2))
        Assertions.assertEquals(25.00, year2000?.monthsMap?.get(4))
        Assertions.assertEquals(1.00, year2000?.monthsMap?.get(12))
        Assertions.assertEquals(176.00, year2000?.total)
    }
}