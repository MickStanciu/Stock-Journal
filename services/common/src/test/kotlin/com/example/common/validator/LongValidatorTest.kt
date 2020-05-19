package com.example.common.validator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LongValidatorTest {

    @Test
    fun `int greater than 0`() {
        assertTrue(LongValidator(1).greaterThanZero().isValid())
        assertFalse(LongValidator(0).greaterThanZero().isValid())
    }
}