package com.example.customer.dao;

import com.example.customer.model.Customer;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class CustomerDao {

    private static final Logger log = Logger.getLogger(CustomerDao.class);

    private static final String CUSTOMER_READ_ALL_QUERY = "SELECT id, first_name, last_name, alias, email, mobile, birthdate " +
            "FROM customers " +
            "WHERE tenant_fk = CAST(:tenant_fk AS uuid) and active = :active";

    private static final String CUSTOMER_READ_FIRST_QUERY = "SELECT id FROM customers c LIMIT 1";

    @PersistenceContext
    private EntityManager em;

    public List<Customer> getCustomers(String tenantId, boolean active) {
        Query q = em.createNativeQuery(CUSTOMER_READ_ALL_QUERY);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("active", active);

        List<Object[]> results = q.getResultList();
        List<Customer> customers = new ArrayList<>();
        for (Object[] result : results) {
            customers.add(mapFromObject(tenantId, active, result));
        }
        return customers;
    }

    public boolean checkFirstRecord() {
        Query q = em.createNativeQuery(CUSTOMER_READ_FIRST_QUERY);
        List<Object[]> results = q.getResultList();
        return results.size() == 1;
    }

    private Customer mapFromObject(String tenantId, boolean active, Object[] result) {
        BigInteger customer_id = ((BigInteger) result[0]);
        String firstName = ((String) result[1]);
        String lastName = ((String) result[2]);
        String alias = ((String) result[3]);
        String email = ((String) result[4]);
        String mobile = ((String) result[5]);
        Date bday = ((Date) result[6]);

        return new Customer.Builder()
                .withId(customer_id)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withAlias(alias)
                .withEmail(email)
                .withMobile(mobile)
                .withBirthDate(bday)
                .withTenantId(tenantId)
                .withFlagActive(active)
                .build();
    }

}
