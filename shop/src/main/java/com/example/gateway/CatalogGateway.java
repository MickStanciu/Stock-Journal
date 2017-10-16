package com.example.gateway;


import com.example.utils.UrlUtils;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

@Stateless
public class CatalogGateway {

    private static String HTTP_PROTOCOL = "http";
    private static int HTTP_PORT = 8080;
    private static String HOST_URL = "localhost";
    private static String SERVICE_BASE_URL = "/catalog/api";


    public void getCatalogItem(int id) {
        String serviceUrl = "/" + id;
        String fullUrl = UrlUtils.getServiceBaseURL(HTTP_PROTOCOL, HTTP_PORT, HOST_URL, SERVICE_BASE_URL);
        Client client = ClientBuilder.newClient();
        Response response = client.target(fullUrl + serviceUrl).request().get();
        Object entity = response.getEntity();
        System.out.println("Expecting: " + "http://localhost:8080/catalog/api/2");
        System.out.println("Got      : " + fullUrl + serviceUrl);
        System.out.println(entity);
    }
}
