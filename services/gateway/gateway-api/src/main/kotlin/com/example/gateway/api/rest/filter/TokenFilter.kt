package com.example.gateway.api.rest.filter

import com.example.gateway.api.rest.converter.TokenConverter
import org.slf4j.LoggerFactory
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenFilter: Filter {

    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, filterChain: FilterChain?) {
        val request = servletRequest as HttpServletRequest
        val response = servletResponse as HttpServletResponse

        if (request.method in listOf<String>("GET", "PUT", "DELETE", "POST")) {
            val token = request.getHeader(AUTH_KEY)

            if (!TokenConverter.validate(token)) {
                LOG.error("Token validation error while serving: ${request.method} ${request.requestURI}")
                request.getRequestDispatcher("/api/v1/error/401").forward(request, response)
                return
            }
        }

        filterChain?.doFilter(request, response)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(TokenFilter::class.java)
        const val AUTH_KEY = "auth-key";
    }
}
