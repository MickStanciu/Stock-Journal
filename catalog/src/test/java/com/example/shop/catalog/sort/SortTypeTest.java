package com.example.shop.catalog.sort;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SortTypeTest {

    @Test
    public void testValidValues() {
        SortType sortType = SortType.lookup("PRICE_DESC", SortType.MODEL_ASC);
        assertEquals("Expected PRICE_DESC", SortType.PRICE_DESC, sortType);
    }

    @Test
    public void testDefaultValue() {
        SortType sortType = SortType.lookup("BLAH", SortType.MODEL_ASC);
        assertEquals("Expected MODEL_ASC", SortType.MODEL_ASC, sortType);
    }
}
