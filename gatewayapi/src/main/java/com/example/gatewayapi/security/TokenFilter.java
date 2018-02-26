package com.example.gatewayapi.security;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = "/*")
public class TokenFilter extends HttpFilter {

    private List<String> bypassFilter;

    public TokenFilter() {
        bypassFilter = Arrays.asList("/health/check");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            if(!bypassFilter.contains(request.getPathInfo())) {
                System.out.println("lets check for token");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
