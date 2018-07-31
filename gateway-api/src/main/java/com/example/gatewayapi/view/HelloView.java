package com.example.gatewayapi.view;

import org.jboss.resteasy.plugins.providers.html.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@Produces(MediaType.TEXT_HTML)
public class HelloView {

    @GET
    public View hello() {
        System.out.println("YO");
        return new View("/index.htm");
    }
}
