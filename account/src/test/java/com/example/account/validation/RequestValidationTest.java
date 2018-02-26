package com.example.account.validation;

import com.example.account.model.Account;
import com.example.account.model.Role;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static com.example.account.validation.RequestValidation.validateCreateAccount;
import static com.example.account.validation.RequestValidation.validateGetAccount;
import static com.example.account.validation.RequestValidation.validateUpdateAccount;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestValidationTest {

    private static String DEFAULT_TENANT_ID = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
    private static BigInteger DEFAULT_ACCOUNT_ID = BigInteger.ONE;
    private static String DEFAULT_ACCOUNT_NAME = "name.surname";
    private static String DEFAULT_ACCOUNT_PASSWORD = "secret";
    private static String DEFAULT_ACCOUNT_EMAIL = "not.set@domain.com";

    @Test
    void testValidateGetAccountWhenValid() {
        boolean response = validateGetAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_NAME, DEFAULT_ACCOUNT_PASSWORD);
        assertTrue(response, "Should be TRUE");
    }


    @Test
    void testValidateGetAccountWhenTenantIdIsInvalid() {
        boolean response = validateGetAccount(null, DEFAULT_ACCOUNT_NAME, DEFAULT_ACCOUNT_PASSWORD);
        assertFalse(response, "Should be FALSE");

        response = validateGetAccount("!23", DEFAULT_ACCOUNT_NAME, DEFAULT_ACCOUNT_PASSWORD);
        assertFalse(response, "Should be FALSE");
    }


    @Test
    void testValidateGetAccountWhenAccountNameIsInvalid() {
        boolean response = validateGetAccount(DEFAULT_TENANT_ID, null, DEFAULT_ACCOUNT_PASSWORD);
        assertFalse(response, "Should be FALSE");

        response = validateGetAccount(DEFAULT_TENANT_ID, "", DEFAULT_ACCOUNT_PASSWORD);
        assertFalse(response, "Should be FALSE");
    }


    @Test
    void testValidateGetAccountWhenAccountPasswordIsInvalid() {
        boolean response = validateGetAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_NAME, null);
        assertFalse(response, "Should be FALSE");

        response = validateGetAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_NAME, "");
        assertFalse(response, "Should be FALSE");

        response = validateGetAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_NAME, "123");
        assertFalse(response, "Should be FALSE");
    }


    @Test
    void testValidateCreateAccountWhenValid() {
        Role roleFixture = new Role(5, "L5", "");
        Account accountFixture = new Account(null, null, roleFixture, DEFAULT_ACCOUNT_NAME,
                DEFAULT_ACCOUNT_EMAIL, DEFAULT_ACCOUNT_PASSWORD, null);
        boolean response = validateCreateAccount(DEFAULT_TENANT_ID, accountFixture);
        assertTrue(response, "Should be TRUE");
    }


    @Test
    void testValidateCreateAccountWhenAccountIsInvalid() {
        Role roleFixture = new Role(5, "L5", "");

        //name
        Account accountFixture = new Account(DEFAULT_TENANT_ID, null, roleFixture, null,
                DEFAULT_ACCOUNT_EMAIL, DEFAULT_ACCOUNT_PASSWORD, true);
        boolean response = validateCreateAccount(DEFAULT_TENANT_ID, accountFixture);
        assertFalse(response, "Should be FALSE");

        //email
        accountFixture = new Account(DEFAULT_TENANT_ID, null, roleFixture, DEFAULT_ACCOUNT_NAME,
                null, DEFAULT_ACCOUNT_PASSWORD, true);
        response = validateCreateAccount(DEFAULT_TENANT_ID, accountFixture);
        assertTrue(response, "Should be TRUE");

        //password
        accountFixture = new Account(DEFAULT_TENANT_ID, null, roleFixture, DEFAULT_ACCOUNT_NAME,
                DEFAULT_ACCOUNT_EMAIL, null, true);
        response = validateCreateAccount(DEFAULT_TENANT_ID, accountFixture);
        assertFalse(response, "Should be FALSE");
    }


    @Test
    void testValidateUpdateAccountWhenValid() {
        Role roleFixture = new Role(5, "L5", "");
        Account accountFixture = new Account(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, roleFixture, DEFAULT_ACCOUNT_NAME,
                DEFAULT_ACCOUNT_EMAIL, DEFAULT_ACCOUNT_PASSWORD, true);
        boolean response = validateUpdateAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, accountFixture);
        assertTrue(response, "Should be TRUE");
    }


    @Test
    void testValidateUpdateAccountWhenTenantIsInvalid() {
        Role roleFixture = new Role(5, "L5", "");
        Account accountFixture = new Account(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, roleFixture, DEFAULT_ACCOUNT_NAME,
                DEFAULT_ACCOUNT_EMAIL, DEFAULT_ACCOUNT_PASSWORD, true);

        boolean response = validateUpdateAccount(null, DEFAULT_ACCOUNT_ID, accountFixture);
        assertFalse(response, "Should be FALSE");
    }


    @Test
    void testValidateUpdateAccountWhenAccountIdIsInvalid() {
        Role roleFixture = new Role(5, "L5", "");
        Account accountFixture = new Account(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, roleFixture, DEFAULT_ACCOUNT_NAME,
                DEFAULT_ACCOUNT_EMAIL, DEFAULT_ACCOUNT_PASSWORD, true);

        boolean response = validateUpdateAccount(DEFAULT_TENANT_ID, null, accountFixture);
        assertFalse(response, "Should be FALSE");
    }


    @Test
    void testValidateUpdateAccountWhenAccountIsInvalid() {
        Role roleFixture = new Role(5, "L5", "");

        //name
        Account accountFixture = new Account(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, roleFixture, "a",
                DEFAULT_ACCOUNT_EMAIL, DEFAULT_ACCOUNT_PASSWORD, true);
        boolean response = validateUpdateAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, accountFixture);
        assertFalse(response, "Should be FALSE");

        //email
        accountFixture = new Account(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, roleFixture, DEFAULT_ACCOUNT_NAME,
                "1", DEFAULT_ACCOUNT_PASSWORD, true);
        response = validateUpdateAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, accountFixture);
        assertFalse(response, "Should be FALSE");

        //password
        accountFixture = new Account(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, roleFixture, DEFAULT_ACCOUNT_NAME,
                DEFAULT_ACCOUNT_EMAIL, "123", true);
        response = validateUpdateAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, accountFixture);
        assertFalse(response, "Should be FALSE");
    }

}
