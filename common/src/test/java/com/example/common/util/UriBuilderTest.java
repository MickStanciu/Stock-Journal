package com.example.common.util;

import org.testng.Assert;
import org.testng.annotations.Test;


public class UriBuilderTest {

    @Test
    public void uriTest() {
        UriBuilder builder = UriBuilder.builder()
                .withPort(8082)
                .addPath("api")
                .addPath("v1")
                .addPath("123-123-123")
                .addPath(6)
                .addQuery("plm", "vas")
                .addQuery("plm2", "vas2")
                .addQuery("plm3", 3);
        String uri = builder.build().toString();
        String expected = "http://localhost:8082/api/v1/123-123-123/6?plm=vas&plm2=vas2&plm3=3";
        Assert.assertEquals(uri, expected,"Failed");
    }

    @Test
    public void uriSchemaTest() {
        UriBuilder builder = UriBuilder.builder("http://localhost:8082")
                .addPath("api")
                .addPath("v1")
                .addPath("123-123-123")
                .addPath(6)
                .addQuery("plm", "vas")
                .addQuery("plm2", "vas2")
                .addQuery("plm3", 3);
        String uri = builder.build().toString();
        String expected = "http://localhost:8082/api/v1/123-123-123/6?plm=vas&plm2=vas2&plm3=3";
        Assert.assertEquals(uri, expected,"Failed");
    }
}
