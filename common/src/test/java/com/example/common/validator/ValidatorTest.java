package com.example.common.validator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorTest {


    @Test
    public void testStringNotNull() {
        String test = "abc";
        StringValidator validator = new StringValidator(test);
        assertTrue("Should be TRUE", validator.notNull().isValid());
    }

    @Test
    public void testNullStringNotNull() {
        StringValidator validator = new StringValidator(null);
        assertFalse("Should be FALSE", validator.notNull().isValid());
    }

    @Test
    public void testStringSize() {
        String test = "abc";
        StringValidator validator = new StringValidator(test);
        assertTrue("Should be TRUE", validator.sizeEqualTo(3).isValid());
        assertFalse("Should be FALSE", validator.sizeEqualTo(0).isValid());
    }

    @Test
    public void testEmptyStringSize() {
        String test = "";
        StringValidator validator = new StringValidator(test);
        assertTrue("Should be TRUE", validator.sizeEqualTo(0).isValid());
        assertFalse("Should be FALSE", validator.sizeEqualTo(1).isValid());
    }

    @Test
    public void testStringLTE() {
        String test = "123456789";
        StringValidator validator = new StringValidator(test);
        assertTrue("Should be TRUE", validator.sizeLessOrEqualTo(9).isValid());
        assertTrue("Should be TRUE", validator.sizeLessOrEqualTo(10).isValid());
        assertFalse("Should be FALSE", validator.sizeLessOrEqualTo(3).isValid());
    }
}
