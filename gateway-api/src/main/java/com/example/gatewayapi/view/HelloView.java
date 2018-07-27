package com.example.gatewayapi.view;

import org.jboss.resteasy.plugins.providers.html.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hello")
@Produces("text/html")
public class HelloView {

    @GET
    public View hello() {
        return new View("index.htm");
    }
}
