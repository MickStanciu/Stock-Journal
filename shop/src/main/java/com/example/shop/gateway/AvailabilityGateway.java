package com.example.shop.gateway;

import com.example.shop.model.AvailabilityItem;
import com.example.shop.model.AvailabilityItemResponse;
import com.example.shop.utils.UrlUtils;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class AvailabilityGateway extends HttpGateway {

    private static final Logger log = Logger.getLogger(AvailabilityGateway.class);
    private static String HTTP_PROTOCOL = "http";
    private static int HTTP_PORT = 8080;
    private static String HOST_URL = "localhost";
    private static String SERVICE_BASE_URL = "/availability/api";


    public Map<Integer, AvailabilityItem> getBulkAvailability(List<Integer> itemIds) {
        String itemIdsCsv = itemIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        String serviceUrl = "/ids/" + itemIdsCsv;
        String fullUrl = UrlUtils.getServiceBaseURL(HTTP_PROTOCOL, HTTP_PORT, HOST_URL, SERVICE_BASE_URL);
        AvailabilityItemResponse response = (AvailabilityItemResponse) get(fullUrl + serviceUrl, AvailabilityItemResponse.class);

        //todo: process errors here and throw the appropriate exceptions
        return response.getData().stream().collect(Collectors.toMap(AvailabilityItem::getItemFk, Function.identity()));
    }
}
