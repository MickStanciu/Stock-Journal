package com.example.gatewayapi.filter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SkipFilterTest {
    private TokenFilter tf = new TokenFilter();

    @BeforeClass
    public void init() {
        tf.init(null);
    }

    @DataProvider(name = "url")
    public static Object[][] urls() {
        return new Object[][] {
                {"/rest/hello", true},
                {"/rest/v1/auth", true},
                {"/rest/v1/health", true},
                {"/rest/v1/test", false}};
    }

    @Test(dataProvider = "url")
    public void filterTest(String url, boolean skip) {
        assertEquals(tf.skipFilter(url), skip, url);
    }

}
