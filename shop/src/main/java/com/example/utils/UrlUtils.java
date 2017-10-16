package com.example.utils;

public class UrlUtils {

    public static String getServiceBaseURL(String protocol, int port, String hostUrl, String baseUrl) {
        return protocol + "://" + hostUrl + ((port == 80 || port == 443) ? "" : ":" + port) + baseUrl;
    }
}
