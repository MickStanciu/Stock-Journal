package com.example.availability.service;

import com.example.availability.model.Stock;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Stateless
public class StockService {

    private static final Logger log = Logger.getLogger(StockService.class);

    @PersistenceContext
    private EntityManager em;

    public Stock getStock(Integer itemId) {
        Query q = em.createNativeQuery(
                "SELECT item_fk, quantity FROM stock WHERE item_fk = ?"
        );
        q.setParameter(1, itemId);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return null;
        }

        return mapFromObject(results.get(0));
    }

    private Stock mapFromObject(Object[] result) {
        int itemId = ((BigInteger) result[0]).intValue();
        int quantity = ((BigInteger) result[1]).intValue();

        return new Stock(itemId, quantity);
    }
}
