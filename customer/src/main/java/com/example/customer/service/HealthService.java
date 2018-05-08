package com.example.customer.service;


import com.example.customer.dao.CustomerDao;
import com.example.customer.model.Health;

import javax.ejb.Stateful;
import javax.inject.Inject;

@Stateful
public class HealthService {

    private final Health health;

    @Inject
    private CustomerDao customerDao;

    public HealthService() {
        health = new Health();
        health.setFirstRecordOk(false);
    }

    public boolean isOk() {
        boolean firstRecordOk;
        try {
            firstRecordOk = customerDao.checkFirstRecord();
        } catch (Exception e) {
            firstRecordOk = false;
        }
        health.setFirstRecordOk(firstRecordOk);
        return firstRecordOk;
    }

    public Health getHealth() {
        return health;
    }

}
