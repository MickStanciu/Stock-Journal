package com.example.gatewayapi.rest;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;

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
        String projectId = "newagent-9bc4e";
        String sessionId = "abc-def";
        String languageCode = "en";
        String text = "I want to cancel";
        //KEY        AIzaSyDbFttfnIgph8ftIqIP6KetaXTOUeQ_M9M
        //GOOGLE_APPLICATION_CREDENTIALS=/Users/mick/Projects/newagent-9bc4e-1e7abf49f31c.json


        //https://cloud.google.com/dialogflow-enterprise/docs/reference/rest/v2/projects.agent.intents/list WORKS

//        GoogleCredentials credentials = GoogleCredentials.fromStream(getResources().getAssets().open("Test-9d8a744b520a.json"))
//                .createScoped(newArrayList("https://www.googleapis.com/auth/compute"));
//        credentials.toBuilder().build();
//
//        AgentsSettings.Builder agentsSettingsBuilder = AgentsSettings.newBuilder();
//        agentsSettingsBuilder.getAgentSettings().getRetrySettings().toBuilder()
//                .setTotalTimeout(Duration.ofSeconds(30));
////        agentsSettingsBuilder.getCredentialsProvider().getCredentials().getRequestMetadata().put()
//        AgentsSettings settings = agentsSettingsBuilder.build();

        try (SessionsClient sessionsClient = SessionsClient.create()) {
            SessionName session = SessionName.of(projectId, sessionId);

            TextInput.Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
        }
//        try (AgentsClient agentsClient = AgentsClient.create(settings)) {
//            ProjectName parent = ProjectName.of("[ChatBot]");
//            Agent response = agentsClient.getAgent(parent);
//        }

        return Response
                .status(Response.Status.OK)
                .build();
    }
}
