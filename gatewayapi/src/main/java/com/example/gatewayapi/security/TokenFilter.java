package com.example.gatewayapi.security;



import com.example.common.security.TokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "tokenFilter", urlPatterns = "/*")
public class TokenFilter extends HttpFilter {

    private static Set<String> absolutePath;
    private static Set<String> startWithPath;
    private static final String AUTH_KEY = "authkey";

    TokenFilter() {
        absolutePath = new HashSet<>();
        absolutePath.add("/api/health/check");
        absolutePath.add("/api/health/check/");
        absolutePath.add("/api/error/401");
        absolutePath.add("/api/error/401/");

        startWithPath = new HashSet<>();
        startWithPath.add("/api/auth/");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if(!skipFilter(request.getRequestURI())) {
            String token = request.getHeader(AUTH_KEY);
            if (!TokenUtil.validateToken(token)) {
                response.sendRedirect("/api/error/401");
                return;
            }
        }

        chain.doFilter(request, response);
    }

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
}
