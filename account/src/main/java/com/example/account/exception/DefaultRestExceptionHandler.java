package com.example.account.exception;

import org.apache.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultRestExceptionHandler implements ExceptionMapper<WebApplicationException> {
    private static final Logger log = Logger.getLogger(DefaultRestExceptionHandler.class);

    @Override
    public Response toResponse(WebApplicationException e) {
        log.error(e);
        return Response.serverError().build();
    }
}
