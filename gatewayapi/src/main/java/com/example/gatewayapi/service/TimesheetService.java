package com.example.gatewayapi.service;

import com.example.gatewayapi.gateway.accountApi.AccountGateway;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TimesheetService {

    private static final Logger log = Logger.getLogger(TimesheetService.class);

    @Inject
    private AccountGateway accountGateway;

//    @Inject
//    private TimesheetGateway timesheetGateway;
}
