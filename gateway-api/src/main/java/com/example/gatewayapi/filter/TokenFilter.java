package com.example.gatewayapi.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TokenFilter implements Filter {

    private final String API_PATH = "/api";
    private final String WEB_PATH = "/webapp";
    private final String AUTH_KEY = "authkey";
    private Set<String> absolutePath = new HashSet<>();
    private Set<String> startWithPath = new HashSet<>();

    boolean skipFilter(String uri) {
        if (!uri.startsWith(API_PATH)) {
            return true;
        }

        if (absolutePath.contains(uri)) {
            return true;
        }

        for (String path : startWithPath) {
            if (uri.startsWith(path) && uri.length() >= path.length()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        startWithPath.add(API_PATH + "/v1/auth");
        startWithPath.add(API_PATH + "/v1/health");
        startWithPath.add(API_PATH + "/v1/error");
        startWithPath.add(API_PATH + "/hello");
        startWithPath.add(API_PATH + "/v1/sample");
        startWithPath.add(API_PATH + WEB_PATH);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(!skipFilter(request.getRequestURI())) {
            String token = request.getHeader(AUTH_KEY);
            if (token == null || token.length() == 0) {
                response.sendRedirect(API_PATH + "/v1/error/401");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

