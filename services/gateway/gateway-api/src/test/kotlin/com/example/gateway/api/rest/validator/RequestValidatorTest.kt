package com.example.gateway.api.rest.validator

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RequestValidatorTest {

    @ParameterizedTest
    @MethodSource("userNameProvider")
    fun testUsername(input: String, pass: Boolean) {
        Assertions.assertEquals(pass, RequestValidator.validateAuthenticate(input, "!1LolaWhite"))
    }

    @ParameterizedTest
    @MethodSource("passwordProvider")
    fun testPassword(input: String, pass: Boolean) {
        Assertions.assertEquals(pass, RequestValidator.validateAuthenticate("LolaWhite", input))
    }

    private fun userNameProvider(): Stream<Arguments> {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of("  ", false),
                Arguments.of("not blank", false),
                Arguments.of("Lola White", false),
                Arguments.of("LolaWhite44", true),
                Arguments.of("Lola44White44", false),
                Arguments.of("LolaWhite", true),
                Arguments.of("Lola", false),
                Arguments.of("LolaLolaLolaLolaLolaLolaLolaLolaLolaLolaLolaLola", false)
        )
    }

    private fun passwordProvider(): Stream<Arguments> {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of("  ", false),
                Arguments.of("not", false),
                Arguments.of("LolaWhite44", true),
                Arguments.of("Lola44White44", true),
                Arguments.of("LolaWhite", true),
                Arguments.of("!aA0", false),
                Arguments.of("!LolaWhite", true),
                Arguments.of("!1LolaWhite~", true)
        )
    }
}