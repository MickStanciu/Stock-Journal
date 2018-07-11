package com.example.gatewayapi.gateway;

import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.common.util.UriBuilder;
import com.example.gatewayapi.configuration.Property;
import com.example.timesheet.model.TimeSheetEntryModel;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.net.URI;
import java.util.Collections;
import java.util.List;

public class pula extends AbstractGateway {

    private static final Logger log = LoggerFactory.getLogger(pula.class);

    @Inject
    @Property("gateway.timesheet.address")
    private String SERVICE_URL = "localhost";

    public List<TimeSheetEntryModel> getTimeSheetEntries(String tenantId, BigInteger accountId, String from, String to) {
        UriBuilder builder = UriBuilder.builder(SERVICE_URL)
                .addPath("api")
                .addPath("v1")
                .addPath(tenantId)
                .addPath(accountId.toString())
                .addQuery("from", from)
                .addQuery("to", to);


        return getTimeSheetModel(builder.build());
    }

    private List<TimeSheetEntryModel> getTimeSheetModel(URI uri) {
        ResteasyWebTarget target = this.getTarget(uri);

        ResponseEnvelope<List<TimeSheetEntryModel>> envelope;

        try {
            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);
            envelope = response.readEntity(new GenericType<ResponseEnvelope<List<TimeSheetEntryModel>>>() {});
        } catch (Exception ex) {
            log.error("Hmmm", ex);
            return Collections.emptyList();
        }

        if (envelope.getErrors() != null) {
            processErrors(envelope.getErrors());
        }

        return envelope.getData();
    }
}
