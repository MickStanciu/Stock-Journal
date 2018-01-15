package com.example.account.validation;

import org.junit.Test;

import static com.example.account.validation.RequestValidation.validateGetAccount;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequestValidationTest {

    @Test
    public void testValidateGetAccountWhenTenantIdIsInvalid() {
        String name = "test";
        String password = "password";

        boolean response = validateGetAccount(null, name, password);
        assertFalse("Should be FALSE", response);

        response = validateGetAccount("!23", name, password);
        assertFalse("Should be FALSE", response);
    }

    @Test
    public void testValidateGetAccountWhenAccountNameIsInvalid() {
        String tenantId = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
        String password = "password";

        boolean response = validateGetAccount(tenantId, null, password);
        assertFalse("Should be FALSE", response);

        response = validateGetAccount(tenantId, "", password);
        assertFalse("Should be FALSE", response);

        response = validateGetAccount(tenantId, "123", password);
        assertFalse("Should be FALSE", response);
    }
}
