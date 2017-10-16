package com.example.shop.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UrlUtilsTest {

    @Test
    public void test80() {
        String fullUrl = UrlUtils.getServiceBaseURL("http", 80, "example.com", "/api/rest");
        assertEquals("Expected http://example.com/api/rest", "http://example.com/api/rest", fullUrl);
    }

    @Test
    public void test8080() {
        String fullUrl = UrlUtils.getServiceBaseURL("http", 8080, "example.com", "/api/rest");
        assertEquals("Expected http://example.com:8080/api/rest", "http://example.com:8080/api/rest", fullUrl);
    }
}
