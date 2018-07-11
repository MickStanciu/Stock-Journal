package com.example.common.util;


import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UriBuilder {
    private String scheme = "http";
    private String host = "localhost";
    private int port = 80;

    private final List<String> paths = new ArrayList<>();
    private final Map<String, String> queries = new HashMap<>();

    private UriBuilder() {
    }

    private UriBuilder(String url) {
        String [] bits = url.split(":");
        if (bits.length >= 1) {
            this.scheme = bits[0];
        }

        if (bits.length >= 2) {
            this.host = bits[1].replace("/", "");
        }

        if (bits.length >= 3) {
            this.port = Integer.valueOf(bits[2]);
        }
    }

    public static UriBuilder builder() {
        return new UriBuilder();
    }

    public static UriBuilder builder(String url) {
        return new UriBuilder(url);
    }

    public UriBuilder withHostName(String name) {
        this.host = name;
        return this;
    }

    public UriBuilder withScheme(String name) {
        this.scheme = name;
        return this;
    }

    public UriBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    public UriBuilder addQuery(String name, String value) {
        queries.put(name, value);
        return this;
    }

    public UriBuilder addQuery(String name, int value) {
        queries.put(name, String.valueOf(value));
        return this;
    }

    public UriBuilder addPath(String name) {
        paths.add(name);
        return this;
    }

    public UriBuilder addPath(int name) {
        paths.add(String.valueOf(name));
        return this;
    }

    public URI build() {
        String schema = String.format("%s://%s", scheme, host);
        StringBuilder uri = new StringBuilder(schema);
        if (port != 80)
        uri.append(String.format(":%d", port));
        uri.append("/");

        if (paths.size() > 0) {
            uri.append(String.join("/", paths));
        }

        if (queries.size() > 0) {
            uri.append("?");
            List<String> pairs = new ArrayList<>();

            queries.forEach( (key, val) -> pairs.add(key + "=" + val));
            uri.append(String.join("&", pairs));
        }

        return URI.create(uri.toString());
    }
}
