package com.example.gatewayapi.view;

import org.jboss.resteasy.plugins.providers.html.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@Produces(MediaType.TEXT_HTML)
public class HelloView {

    @GET
    public View hello(@Context HttpServletResponse response,
                      @Context HttpServletRequest request) {
        System.out.println("YO");
        return new View("/index.htm");
    }
}
