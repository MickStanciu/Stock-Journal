//package com.example.gatewayapi.gateway;
//
//import com.example.common.resource.dto.ErrorDto;
//import com.example.gatewayapi.configuration.JacksonConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.WebTarget;
//import java.net.URI;
//import java.util.List;
//
//abstract class AbstractGateway {
//
//    private static final Logger log = LoggerFactory.getLogger(AbstractGateway.class);
//
//    void processErrors(List<ErrorDto> errors) {
//        for (ErrorDto error : errors) {
//            log.error("", error);
//        }
//    }
//
//    public WebTarget getTarget(URI uri) {
//        Client client = ClientBuilder
//                .newClient()
//                .register(JacksonConfig.class);
//        return client.target(uri);
//    }
//
//}
