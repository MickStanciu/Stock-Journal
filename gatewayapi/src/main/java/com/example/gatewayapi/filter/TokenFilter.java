package com.example.gatewayapi.filter;

import com.example.common.security.TokenUtil;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Order(1)
public class TokenFilter implements Filter {

    private final String AUTH_KEY = "authkey";
    private Set<String> absolutePath = new HashSet<>();
    private Set<String> startWithPath = new HashSet<>();

    boolean skipFilter(String uri) {
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
        absolutePath.add("/api/health/check");
        absolutePath.add("/api/health/check/");
        absolutePath.add("/api/error/401");
        absolutePath.add("/api/error/401/");

        startWithPath.add("/api/auth/");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(!skipFilter(request.getRequestURI())) {
            String token = request.getHeader(AUTH_KEY);
            if (!TokenUtil.validateToken(token)) {
                response.sendRedirect("/api/error/401");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
