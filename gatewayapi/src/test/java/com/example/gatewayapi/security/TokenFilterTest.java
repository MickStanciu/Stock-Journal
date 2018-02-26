package com.example.gatewayapi.security;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


public class TokenFilterTest {


    private static TokenFilter tokenFilter;

    @DataProvider(name = "skip")
    public static Object[][] shouldSkipData() {
        return new Object[][] {
                {"/api/health/check"}, {"/api/health/check/"},
                {"/api/auth/123"}, {"/api/error/401"}, {"/api/error/401/"}
        };
    }

    @DataProvider(name = "check")
    public static Object[][] shouldNotSkipData() {
        return new Object[][] {
                {"/api/health"}, {"/api/health/che"},
                {"/api/auth"}, {"/api/auth/"}, {"/api/bla"}
        };
    }


    @BeforeClass
    static void init() {
        tokenFilter = new TokenFilter();
    }



    @Test(dataProvider = "skip")
    void testSkipUrls(String uri) {
        assertTrue(tokenFilter.skipFilter(uri));
    }

    @Test(dataProvider = "check")
    void testDontSkipUrls(String uri) {
        assertFalse(tokenFilter.skipFilter(uri));
    }


}
