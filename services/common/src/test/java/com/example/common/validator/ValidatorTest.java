//package com.example.common.validator;
//
//
//import org.testng.annotations.Test;
//
//import static org.testng.Assert.assertFalse;
//import static org.testng.Assert.assertTrue;
//
//public class ValidatorTest {
//
//    @Test
//    public void testStringNotNull() {
//        String test = "abc";
//        StringValidator validator = new StringValidator(test);
//        assertTrue(validator.notNull().isValid(), "Should be TRUE");
//    }
//
//    @Test
//    public void testNullStringNotNull() {
//        StringValidator validator = new StringValidator(null);
//        assertFalse(validator.notNull().isValid(), "Should be FALSE");
//    }
//
//    @Test
//    public void testStringSize() {
//        String test = "abc";
//        StringValidator validator = new StringValidator(test);
//        assertTrue(validator.sizeEqualTo(3).isValid(), "Should be TRUE");
//        assertFalse(validator.sizeEqualTo(0).isValid(), "Should be FALSE");
//    }
//
//    @Test
//    public void testEmptyStringSize() {
//        String test = "";
//        StringValidator validator = new StringValidator(test);
//        assertTrue(validator.sizeEqualTo(0).isValid(), "Should be TRUE");
//        assertFalse(validator.sizeEqualTo(1).isValid(), "Should be FALSE");
//    }
//
//    @Test
//    public void testStringLTE() {
//        String test = "123456789";
//        StringValidator validator = new StringValidator(test);
//        assertTrue(validator.sizeLessOrEqualTo(9).isValid(), "Should be TRUE");
//        assertTrue(validator.sizeLessOrEqualTo(10).isValid(), "Should be TRUE");
//        assertFalse(validator.sizeLessOrEqualTo(3).isValid(), "Should be FALSE");
//    }
//
//    @Test
//    public void testStringGTE() {
//        String test = "123456789";
//        StringValidator validator = new StringValidator(test);
//        assertTrue(validator.sizeGreaterOrEqualTo(9).isValid(), "Should be TRUE");
//        assertTrue(validator.sizeGreaterOrEqualTo(1).isValid(), "Should be TRUE");
//        assertFalse(validator.sizeGreaterOrEqualTo(100).isValid(), "Should be FALSE");
//    }
//
//    @Test
//    public void testIntegerNotNull() {
//        Integer test = 1;
//        IntegerValidator validator = new IntegerValidator(test);
//        assertTrue(validator.notNull().isValid(), "Should be TRUE");
//    }
//
//    @Test
//    public void testIntegerGreaterThanZero1() {
//        Integer test = 1;
//        IntegerValidator validator = new IntegerValidator(test);
//        assertTrue(validator.greaterThanZero().isValid(), "Should be TRUE");
//    }
//
//    @Test
//    public void testIntegerGreaterThanZero2() {
//        Integer test = 0;
//        IntegerValidator validator = new IntegerValidator(test);
//        assertFalse(validator.greaterThanZero().isValid(), "Should be TRUE");
//    }
//}
