package com.example.tenantapi.service;//package com.example.tenant.service;
//
//import com.ecwid.consul.v1.ConsulClient;
//import com.ecwid.consul.v1.agent.model.NewService;
//import org.apache.log4j.Logger;
//
//import javax.ejb.Stateless;
//
//@Stateless
//public class RegistryService {
//
//    private static final Logger log = Logger.getLogger(RegistryService.class);
//
//    private static final String HOST_PROTOCOL = "http";
//    private static final String HOST_NAME = "localhost";
//    private static final int HOST_PORT = 8080;
//
//    public boolean register(String serviceName, String healthEndPoint) {
//        try {
//            NewService.Check serviceCheck = new NewService.Check();
//            serviceCheck.setHttp(getServiceUrl() + healthEndPoint);
//            serviceCheck.setInterval("30s");
//            serviceCheck.setTimeout("600s");
//
//            NewService newService = new NewService();
//            newService.setCheck(serviceCheck);
//            newService.setId(getServiceId(serviceName));
//            newService.setName(serviceName);
//            newService.setAddress(HOST_NAME);
//            newService.setPort(HOST_PORT);
//
//            ConsulClient client = new ConsulClient(getConsulAddress());
//            client.agentServiceRegister(newService);
//        } catch (Exception ex) {
//            log.error("Could not connect to the registry", ex);
//            return false;
//        }
//        log.info("Service Registered");
//        return true;
//    }
//
//    public void unRegister() {
//        log.info("Service Un-Registered");
//
//    }
//
//    private String getServiceUrl() {
//        //todo: detect these dynamically
//        String url = HOST_PROTOCOL + "://" + HOST_NAME;
//
//        if (HOST_PORT != 80) {
//            url += ":" + HOST_PORT;
//        }
//
//        url += "/api";
//        return url;
//    }
//
//    private String getServiceId(String serviceName) {
//        return serviceName + ":" + HOST_NAME + ":" + HOST_PORT;
//    }
//
//    private String getConsulAddress() {
//        return System.getProperty("consul.host", "localhost");
//    }
//}
