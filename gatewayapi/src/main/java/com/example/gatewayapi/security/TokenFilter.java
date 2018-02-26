package com.example.gatewayapi.security;


import com.example.gatewayapi.exception.ApiException;
import com.example.gatewayapi.exception.ExceptionCode;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(filterName = "tokenFilter", urlPatterns = "/*")
public class TokenFilter extends HttpFilter {

    private static final List<String> bypassFilter = Arrays.asList("/health/check", "/error/401");
    private static final String AUTH_KEY = "authkey";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(!skipFilter(request.getRequestURI())) {
            String token = request.getHeader(AUTH_KEY);
            if (!TokenUtil.validateToken(token)) {
                response.sendRedirect("/api/error/401");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    boolean skipFilter(String uri) {
        return true;
    }
}
