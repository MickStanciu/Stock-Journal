package com.example.gatewayapi.security;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenFilterTest {


    private static TokenFilter tokenFilter;

    private static Stream<Arguments> shouldSkipData() {
        return Stream.of(
                Arguments.of("/api/health/check"),
                Arguments.of("/api/health/check/"),
                Arguments.of("/api/auth/123"),
                Arguments.of("/api/error/401"),
                Arguments.of("/api/error/401/")
        );
    }

    private static Stream<Arguments> shouldNotSkipData() {
        return Stream.of(
                Arguments.of("/api/health"),
                Arguments.of("/api/health/che"),
                Arguments.of("/api/auth"),
                Arguments.of("/api/auth/"),
                Arguments.of("/api/bla")
        );
    }


    @BeforeAll
    static void init() {
        tokenFilter = new TokenFilter();
    }

    @DisplayName("Should skip the filter")
    @ParameterizedTest(name = "{index} => URI = {0}")
    @MethodSource("shouldSkipData")
    void testSkipUrls(String uri) {
        assertTrue(tokenFilter.skipFilter(uri));
    }

    @DisplayName("Should not skip the filter")
    @ParameterizedTest(name = "{index} => URI = {0}")
    @MethodSource("shouldNotSkipData")
    void testDontSkipUrls(String uri) {
        assertFalse(tokenFilter.skipFilter(uri));
    }


}
