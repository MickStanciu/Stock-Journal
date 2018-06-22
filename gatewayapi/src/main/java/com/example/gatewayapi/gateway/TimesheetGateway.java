package com.example.gatewayapi.gateway;

import org.springframework.stereotype.Component;

@Component
public class TimesheetGateway extends AbstractGateway {

//    private static final Logger log = LoggerFactory.getLogger(TimesheetGateway.class);
//
//    @Value("${gateway.timesheet.address}")
//    private String SERVICE_URL;
//
//    private WebTarget target;
//
//    @PostConstruct
//    public void init() {
//        Client client = ClientBuilder.newClient();
//        client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
//        client.property(ClientProperties.READ_TIMEOUT,    1000);
//        target = client.target(UriBuilder.fromPath(SERVICE_URL + "/api/v1"));
//    }
//
//
//    public List<TimeSheetEntryModel> getTimesheetEntries(String tenantId, BigInteger accountId, String from, String to) {
//        Response response =
//                target.path("/" + tenantId + "/" + accountId)
//                        .request(MediaType.APPLICATION_JSON)
//                        .get(Response.class);
//
//        return getModel(response);
//    }
//
//    private List<TimeSheetEntryModel> getModel(Response response) {
//        ResponseEnvelope<List<TimeSheetEntryModel>> envelope;
//        try {
//            envelope = response.readEntity(new GenericType<ResponseEnvelope<List<TimeSheetEntryModel>>>(){});
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return Collections.emptyList();
//        } finally {
//            response.close();
//        }
//
//        if (response.getStatus() != 200 && envelope.getErrors() != null) {
//            processErrors(envelope.getErrors());
//        }
//
//        if (response.getStatus() == 200 && envelope.getData() != null) {
//            return envelope.getData();
//        }
//        return Collections.emptyList();
//    }

}
