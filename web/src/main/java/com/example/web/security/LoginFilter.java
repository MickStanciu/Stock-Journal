package com.example.web.security;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "loginFilter")
public class LoginFilter extends HttpFilter {

    private static final Logger log = Logger.getLogger(LoginFilter.class);
    private static final String WEB_PAGE_EXT = ".xhtml";
    private static final String RESOURCES_PATH = "/resources/";
    private static Set<String> absolutePath;
    private static Set<String> startWithPath;

    public LoginFilter() {
        absolutePath = new HashSet<>();
        absolutePath.add("/login.xhtml");

        startWithPath = new HashSet<>();
        startWithPath.add("/login.xhtml");
        startWithPath.add("/xxx.xhtml");
        startWithPath.add("/javax.faces.resource");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getServletPath().contains(WEB_PAGE_EXT) && !skipFilter(req.getRequestURI())) {
            //check for login
            log.warn(req.getRequestURI());
            log.warn("HERE WE WILL DO LOGIN :)..");
            res.sendRedirect("/login.xhtml");
            return;
        }

        if (req.getServletPath().contains(RESOURCES_PATH)) {
            //skip
            chain.doFilter(req, res);
        }

        chain.doFilter(req, res);
    }

    private boolean skipFilter(String uri) {
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


}
