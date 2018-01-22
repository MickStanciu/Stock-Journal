package com.example.customer.service;

import com.example.customer.dao.CustomerDao;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CustomerService {

    private static final Logger log = Logger.getLogger(CustomerService.class);

    @Inject
    private CustomerDao customerDao;
}
