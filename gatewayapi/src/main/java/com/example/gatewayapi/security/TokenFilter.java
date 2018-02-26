package com.example.gatewayapi.security;



import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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

    public TokenFilter() {
        absolutePath = new HashSet<>();
        absolutePath.add("/health/check");
        absolutePath.add("/health/check/");
        absolutePath.add("/error/401");
        absolutePath.add("/error/401/");

        startWithPath = new HashSet<>();
        startWithPath.add("/auth/");
    }

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
