package com.example.shop.gateway;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

public class HttpGateway<T> {

    public T get(String url, Class<T> clazz) {
        Client client = ClientBuilder.newClient();

        Response response = client.target(url).request().get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Request for url " + url + " failed with error code: " + response.getStatus());
        }

        return response.readEntity(clazz);
    }
}
