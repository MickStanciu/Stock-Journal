package com.example.web.security;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginFilter")
public class LoginFilter extends HttpFilter {

    private static final Logger log = Logger.getLogger(LoginFilter.class);
    private static final String WEB_PAGE_EXT = ".xhtml";
    private static final String RESOURCES_PATH = "/resources/";

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getServletPath().contains(WEB_PAGE_EXT)) {
            //check for login
            log.warn("HERE WE WILL DO LOGIN");
        }

        if (req.getServletPath().contains(RESOURCES_PATH)) {
            //skip
            chain.doFilter(req, res);
        }

        chain.doFilter(req, res);
    }


}
