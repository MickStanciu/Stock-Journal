package com.example.common.validator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ValidatorTest {

    @Test
    fun testStringSize() {
        val test = "abc"
        val validator = StringValidator(test)
        assertTrue(validator.sizeEqualTo(3).isValid(), "Should be TRUE")
        assertFalse(validator.sizeEqualTo(0).isValid(), "Should be FALSE")
    }

    @Test
    fun testEmptyStringSize() {
        val test = ""
        val validator = StringValidator(test)
        assertTrue(validator.sizeEqualTo(0).isValid(), "Should be TRUE")
        assertFalse(validator.sizeEqualTo(1).isValid(), "Should be FALSE")
    }

    @Test
    fun testStringLTE() {
        val test = "123456789"
        val validator = StringValidator(test)
        assertTrue(validator.sizeLessOrEqualTo(9).isValid(), "Should be TRUE")
        assertTrue(validator.sizeLessOrEqualTo(10).isValid(), "Should be TRUE")
        assertFalse(validator.sizeLessOrEqualTo(3).isValid(), "Should be FALSE")
    }

    @Test
    fun testStringGTE() {
        val test = "123456789"
        val validator = StringValidator(test)
        assertTrue(validator.sizeGreaterOrEqualTo(9).isValid(), "Should be TRUE")
        assertTrue(validator.sizeGreaterOrEqualTo(1).isValid(), "Should be TRUE")
        assertFalse(validator.sizeGreaterOrEqualTo(100).isValid(), "Should be FALSE")
    }

    @Test
    fun testIntegerGreaterThanZero1() {
        val test = 1
        val validator = IntegerValidator(test)
        assertTrue(validator.greaterThanZero().isValid(), "Should be TRUE")
    }

    @Test
    fun testIntegerGreaterThanZero2() {
        val test = 0
        val validator = IntegerValidator(test)
        assertFalse(validator.greaterThanZero().isValid(), "Should be TRUE")
    }
}