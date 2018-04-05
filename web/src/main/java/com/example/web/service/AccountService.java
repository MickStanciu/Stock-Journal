package com.example.web.service;

import com.example.web.gateway.GatewayApi;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AccountService {


    @Inject
    private GatewayApi gatewayApi;

}
