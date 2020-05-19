package com.example.common.validator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StringValidatorTest {

    @Test
    fun `empty string size`() {
        assertFalse(StringValidator("").notEmpty().isValid())
        assertFalse(StringValidator("    ").notEmpty().isValid())
        assertTrue(StringValidator("x").notEmpty().isValid())
        assertTrue(StringValidator("  x  ").notEmpty().isValid())
    }

    @Test
    fun `string size`() {
        assertTrue(StringValidator("123").sizeEqualTo(3).isValid())
        assertFalse(StringValidator("123").sizeEqualTo(0).isValid())
    }


    @Test
    fun `string less or equal`() {
        val test = "123456789"
        val validator = StringValidator(test)
        assertTrue(validator.sizeLessOrEqualTo(9).isValid())
        assertTrue(validator.sizeLessOrEqualTo(10).isValid())
        assertFalse(validator.sizeLessOrEqualTo(3).isValid())
    }

    @Test
    fun `string greater or equal`() {
        val test = "123456789"
        val validator = StringValidator(test)
        assertTrue(validator.sizeGreaterOrEqualTo(9).isValid())
        assertTrue(validator.sizeGreaterOrEqualTo(1).isValid())
        assertFalse(validator.sizeGreaterOrEqualTo(100).isValid())
    }
}