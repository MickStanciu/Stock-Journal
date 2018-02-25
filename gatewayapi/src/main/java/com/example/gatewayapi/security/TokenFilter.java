package com.example.gatewayapi.security;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class TokenFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("FILTER!");
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
