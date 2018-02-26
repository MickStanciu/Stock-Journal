package com.example.gatewayapi.rest;

import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultRestExceptionHandler implements ExceptionMapper<Exception>{
    private static final Logger log = Logger.getLogger(DefaultRestExceptionHandler.class);


    @Override
    public Response toResponse(Exception e) {
        //todo: build on this
        log.error(e);
        return Response.status(Response.Status.CONFLICT).build();
    }
}
