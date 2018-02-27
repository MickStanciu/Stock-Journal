package com.example.customer.service;

import com.example.customer.dao.CustomerDao;
import com.example.customer.model.Customer;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class CustomerService {

    private static final Logger log = Logger.getLogger(CustomerService.class);

    @Inject
    private CustomerDao customerDao;

    public List<Customer> getActiveCustomers(String tenantId) {
        return customerDao.getCustomers(tenantId, true);
    }

    public List<Customer> getInactiveCustomers(String tenantId) {
        return customerDao.getCustomers(tenantId, false);
    }
}
