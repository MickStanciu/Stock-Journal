package com.example.gatewayapi.gateway;

import com.example.core.model.TimesheetEntryModel;
import com.example.core.rest.TimesheetRestInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Stateless
public class TimesheetGateway extends AbstractGateway {

    @Inject
    @SystemProperty("TIMESHEET_API_ADDRESS")
    private String SERVICE_URL;

    private TimesheetRestInterface proxy;

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());

        ResteasyClient client = new ResteasyClientBuilder()
                .register(mapper)
                .build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(SERVICE_URL + "/api"));
        proxy = target.proxy(TimesheetRestInterface.class);
    }

    public List<TimesheetEntryModel> getTimesheetEntries(String tenantId, BigInteger accountId, String from, String to) {
        Response response = proxy.getTimesheetEntries(tenantId, accountId, from, to);
        ResponseEnvelope<List<TimesheetEntryModel>> envelope = response.readEntity(new GenericType<ResponseEnvelope<List<TimesheetEntryModel>>>(){});
        response.close();

        if (response.getStatus() != 200 && envelope.getErrors() != null) {
            processErrors(envelope.getErrors());
        }

        if (response.getStatus() == 200 && envelope.getData() != null) {
            return envelope.getData();
        }

        return Collections.emptyList();
    }

}
