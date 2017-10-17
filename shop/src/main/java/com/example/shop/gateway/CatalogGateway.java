package com.example.shop.gateway;


import com.example.common.sort.SortType;
import com.example.shop.model.CatalogItemResponse;
import com.example.shop.model.PaginatedCatalogItemResponse;
import com.example.shop.utils.UrlUtils;

import javax.ejb.Stateless;

@Stateless
public class CatalogGateway extends HttpGateway {

    private static String HTTP_PROTOCOL = "http";
    private static int HTTP_PORT = 8080;
    private static String HOST_URL = "localhost";
    private static String SERVICE_BASE_URL = "/catalog/api";

    public CatalogItemResponse getCatalogItem(int id) {
        String serviceUrl = "/" + id;
        String fullUrl = UrlUtils.getServiceBaseURL(HTTP_PROTOCOL, HTTP_PORT, HOST_URL, SERVICE_BASE_URL);
        return (CatalogItemResponse) get(fullUrl + serviceUrl, CatalogItemResponse.class);
    }

    public PaginatedCatalogItemResponse getPaginatedItems(int limit, int offset, SortType sortType) {
        String serviceUrl = "/?offset=" + offset + "&limit=" + limit + "&sort=" + sortType.getName();
        String fullUrl = UrlUtils.getServiceBaseURL(HTTP_PROTOCOL, HTTP_PORT, HOST_URL, SERVICE_BASE_URL);
        return (PaginatedCatalogItemResponse) get(fullUrl + serviceUrl, PaginatedCatalogItemResponse.class);
    }
}
