package com.example.shop.gateway;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;

@Stateless
public class AvailabilityGateway extends HttpGateway {

    private static final Logger log = Logger.getLogger(AvailabilityGateway.class);
    private static String HTTP_PROTOCOL = "http";
    private static int HTTP_PORT = 8080;
    private static String HOST_URL = "localhost";
    private static String SERVICE_BASE_URL = "/availability/api";



}
