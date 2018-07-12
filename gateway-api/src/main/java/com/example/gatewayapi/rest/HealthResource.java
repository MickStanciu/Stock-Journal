package com.example.gatewayapi.rest;

import com.google.cloud.dialogflow.v2.Agent;
import com.google.cloud.dialogflow.v2.AgentsClient;
import com.google.cloud.dialogflow.v2.ProjectName;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/v1/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    @GET
    @Path("/ping")
    public Response pong() {
        return Response
                .status(Response.Status.OK)
                .build();
    }


    @GET
    @Path("/dialog")
    public Response dialogFlow() throws IOException {
        try (AgentsClient agentsClient = AgentsClient.create()) {
            ProjectName parent = ProjectName.of("[ChatBot]");
            Agent response = agentsClient.getAgent(parent);
        }

        return Response
                .status(Response.Status.OK)
                .build();
    }
}
