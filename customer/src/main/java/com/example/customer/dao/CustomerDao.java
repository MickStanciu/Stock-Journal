package com.example.customer.dao;

import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CustomerDao {

    private static final Logger log = Logger.getLogger(CustomerDao.class);

    private static final String CUSTOMER_READ_ALL_QUERY = "SELECT id, first_name, last_name, alias, email, mobile, birthdate " +
            "FROM customers " +
            "WHERE tenant_fk = CAST(:tenant_fk AS uuid) and active = :active";

    @PersistenceContext
    private EntityManager em;

}
