package com.example.common.validator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class IntValidatorTest {

    @Test
    fun `int greater than 0`() {
        assertTrue(IntegerValidator(1).greaterThanZero().isValid())
        assertFalse(IntegerValidator(0).greaterThanZero().isValid())
    }
}