package com.example.catalog.service;

import com.example.catalog.model.ItemDto;
import com.example.catalog.model.Attribute;
import com.example.common.sort.SortType;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Stateless
public class CatalogService {

    private static final Logger log = Logger.getLogger(CatalogService.class);

    @PersistenceContext
    private EntityManager em;

    public ItemDto getItem(Integer itemId) {
        Query q = em.createNativeQuery(
                "SELECT i.id, i.model, i.sku, i.price, " +
                    "a.description, ia.value " +
                    "FROM item i " +
                    "LEFT JOIN item_attribute ia ON ia.item_id = i.id " +
                    "LEFT JOIN attribute a ON ia.attribute_id = a.id " +
                    "WHERE i.id = ?");
        q.setParameter(1, itemId);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return null;
        }

        return mapFromObject(results.get(0));
    }

    public List<BigInteger> getIdList(int limit, int offset, SortType sortType) {
        String query = "SELECT id FROM item ORDER BY :sort LIMIT :limit OFFSET :offset";
        query = query.replace(":sort", sortType.getSort());

        Query q = em.createNativeQuery(query);
        q.setParameter("limit", limit);
        q.setParameter("offset", offset);

        List<BigInteger> results = q.getResultList();
        if (results.size() == 0) {
            return Collections.emptyList();
        }
        return results;
    }

    public List<ItemDto> getItemList(List<BigInteger> paginatedIds, SortType sortType) {

        String query = "SELECT i.id, i.model, i.sku, i.price, " +
                "a.description, ia.value " +
                "FROM item i " +
                "LEFT JOIN item_attribute ia ON ia.item_id = i.id " +
                "LEFT JOIN attribute a ON ia.attribute_id = a.id " +
                "WHERE i.id in :inclList " +
                "ORDER BY :sort";

        query = query.replace(":sort", sortType.getSort());

        Query q = em.createNativeQuery(query);
        q.setParameter("inclList", paginatedIds);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return Collections.emptyList();
        }

        Map<Integer, ItemDto> items = new LinkedHashMap<>();
        for(Object[] o : results) {
            ItemDto item = mapFromObject(o);

            if (items.containsKey(item.getId())) {
                // pile up the attributes
                ItemDto originalItem = items.get(item.getId());
                originalItem.getAttributes().addAll(item.getAttributes());
                items.put(item.getId(), originalItem);
            } else {
                items.put(item.getId(), item);
            }
        }

        return new ArrayList<>(items.values());
    }

    private ItemDto mapFromObject(Object[] result) {
        int id = ((BigInteger) result[0]).intValue();
        String model = (String) result[1];
        String sku = (String) result[2];
        BigDecimal price = (BigDecimal) result[3];

        ItemDto item = new ItemDto(id, model, sku, price);
        Set<Attribute> attributes = new HashSet<>();
        String name = (String) result[4];
        String value = (String) result[5];

        if (name != null && value != null) {
            attributes.add(new Attribute(name, value));
        }

        item.setAttributes(attributes);
        return item;
    }
}
