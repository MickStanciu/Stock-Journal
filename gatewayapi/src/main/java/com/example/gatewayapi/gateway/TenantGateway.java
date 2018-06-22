package com.example.gatewayapi.gateway;

import org.springframework.stereotype.Component;

@Component
public class TenantGateway extends AbstractGateway {

//    private static final Logger log = LoggerFactory.getLogger(TenantGateway.class);
//
//    @Value("${gateway.tenant.address}")
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
//    public Optional<TenantModel> getTenant(String tenantId) {
//        Response response =
//            target.path("/" + tenantId)
//                    .request(MediaType.APPLICATION_JSON)
//                    .get(Response.class);
//
//        return getModel(response);
//    }
//
//    private Optional<TenantModel> getModel(Response response) {
//        ResponseEnvelope<TenantModel> envelope;
//        try {
//            envelope = response.readEntity(new GenericType<ResponseEnvelope<TenantModel>>(){});
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return Optional.empty();
//        } finally {
//            response.close();
//        }
//
//        if (response.getStatus() != 200 && envelope.getErrors() != null) {
//            processErrors(envelope.getErrors());
//        }
//
//        if (response.getStatus() == 200 && envelope.getData() != null) {
//            return Optional.of(envelope.getData());
//        }
//        return Optional.empty();
//    }

}
