package com.example.common.validator

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidatorTest {

    @Test
    fun testStringNotNull() {
        val test = "abc"
        val validator = StringValidator(test)
        Assertions.assertTrue(validator.notNull().isValid, "Should be TRUE")
    }

    @Test
    fun testNullStringNotNull() {
        val validator = StringValidator(null)
        Assertions.assertFalse(validator.notNull().isValid, "Should be FALSE")
    }

    @Test
    fun testStringSize() {
        val test = "abc"
        val validator = StringValidator(test)
        Assertions.assertTrue(validator.sizeEqualTo(3).isValid, "Should be TRUE")
        Assertions.assertFalse(validator.sizeEqualTo(0).isValid, "Should be FALSE")
    }

    @Test
    fun testEmptyStringSize() {
        val test = ""
        val validator = StringValidator(test)
        Assertions.assertTrue(validator.sizeEqualTo(0).isValid, "Should be TRUE")
        Assertions.assertFalse(validator.sizeEqualTo(1).isValid, "Should be FALSE")
    }

    @Test
    fun testStringLTE() {
        val test = "123456789"
        val validator = StringValidator(test)
        Assertions.assertTrue(validator.sizeLessOrEqualTo(9).isValid, "Should be TRUE")
        Assertions.assertTrue(validator.sizeLessOrEqualTo(10).isValid, "Should be TRUE")
        Assertions.assertFalse(validator.sizeLessOrEqualTo(3).isValid, "Should be FALSE")
    }

    @Test
    fun testStringGTE() {
        val test = "123456789"
        val validator = StringValidator(test)
        Assertions.assertTrue(validator.sizeGreaterOrEqualTo(9).isValid, "Should be TRUE")
        Assertions.assertTrue(validator.sizeGreaterOrEqualTo(1).isValid, "Should be TRUE")
        Assertions.assertFalse(validator.sizeGreaterOrEqualTo(100).isValid, "Should be FALSE")
    }

    @Test
    fun testIntegerNotNull() {
        val test = 1
        val validator = IntegerValidator(test)
        Assertions.assertTrue(validator.notNull().isValid, "Should be TRUE")
    }

    @Test
    fun testIntegerGreaterThanZero1() {
        val test = 1
        val validator = IntegerValidator(test)
        Assertions.assertTrue(validator.greaterThanZero().isValid, "Should be TRUE")
    }

    @Test
    fun testIntegerGreaterThanZero2() {
        val test = 0
        val validator = IntegerValidator(test)
        Assertions.assertFalse(validator.greaterThanZero().isValid, "Should be TRUE")
    }

    @ParameterizedTest
    @MethodSource("userNameProvider")
    fun testRegex(input: String, pass: Boolean) {
        val pattern = "(?:[a-zA-Z]){3,}(?:[0-9])*"
        val validator = StringValidator(input)
        Assertions.assertEquals(pass, validator.regex(pattern).isValid)
    }

    fun userNameProvider(): Stream<Arguments> {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of("  ", false),
                Arguments.of("not blank", false),
                Arguments.of("Lola White", false),
                Arguments.of("LolaWhite44", true),
                Arguments.of("Lola44White44", false),
                Arguments.of("LolaWhite", true)
        )
    }

}