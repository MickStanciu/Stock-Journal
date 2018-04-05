package com.example.web.beans;

import com.example.web.gateway.GatewayApi;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("DashboardBean")
@RequestScoped
public class DashboardBean {

    private static final Logger log = Logger.getLogger(DashboardBean.class);

    @Inject
    private GatewayApi gatewayApi;
}
