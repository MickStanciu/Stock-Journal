package com.example.shop.gateway;


import com.example.shop.model.CatalogItem;
import com.example.shop.model.CatalogItemResponse;
import com.example.shop.utils.UrlUtils;

import javax.ejb.Stateless;

@Stateless
public class CatalogGateway extends HttpGateway<CatalogItemResponse> {

    private static String HTTP_PROTOCOL = "http";
    private static int HTTP_PORT = 8080;
    private static String HOST_URL = "localhost";
    private static String SERVICE_BASE_URL = "/catalog/api";

    public CatalogItem getCatalogItem(int id) {
        String serviceUrl = "/" + id;
        String fullUrl = UrlUtils.getServiceBaseURL(HTTP_PROTOCOL, HTTP_PORT, HOST_URL, SERVICE_BASE_URL);
        CatalogItemResponse entity = get(fullUrl + serviceUrl, CatalogItemResponse.class);
        return entity.getData();
    }
}
