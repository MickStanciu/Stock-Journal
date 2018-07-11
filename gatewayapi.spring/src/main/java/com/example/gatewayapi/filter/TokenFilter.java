package com.example.gatewayapi.filter;

import org.springframework.core.annotation.Order;

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

@Order(1)
public class TokenFilter implements Filter {

    private final String API_PATH = "/api";
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
            if (uri.startsWith(path) && uri.length() > path.length()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        startWithPath.add("/api/v1/auth/");
        startWithPath.add("/api/v1/health");
        startWithPath.add("/api/v1/error");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(!skipFilter(request.getRequestURI())) {
            String token = request.getHeader(AUTH_KEY);
            if (token == null || token.length() == 0) {
                response.sendRedirect("/api/v1/error/401");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
