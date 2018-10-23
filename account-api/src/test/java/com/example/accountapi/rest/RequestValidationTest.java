package com.example.accountapi.rest;


import com.example.accountapi.model.AccountModel;
import com.example.accountapi.model.RoleModel;
import org.junit.Assert;
import org.junit.Test;

import static com.example.accountapi.rest.RequestValidation.*;

public class RequestValidationTest {

    private static final String DEFAULT_TENANT_ID = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
    private static final long DEFAULT_ACCOUNT_ID = 1L;
    private static final String DEFAULT_ACCOUNT_NAME = "name.surname";
    private static final String DEFAULT_ACCOUNT_PASSWORD = "secret";
    private static final String DEFAULT_ACCOUNT_EMAIL = "not.set@domain.com";

    @Test
    public void testValidateGetAccountWhenValid() {
        boolean response = validateGetAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_NAME, DEFAULT_ACCOUNT_PASSWORD);
        Assert.assertTrue("Should be TRUE", response);
    }


    @Test
    public void testValidateGetAccountWhenTenantIdIsInvalid() {
        boolean response = validateGetAccount(null, DEFAULT_ACCOUNT_NAME, DEFAULT_ACCOUNT_PASSWORD);
        Assert.assertFalse("Should be FALSE", response);

        response = validateGetAccount("!23", DEFAULT_ACCOUNT_NAME, DEFAULT_ACCOUNT_PASSWORD);
        Assert.assertFalse("Should be FALSE", response);
    }


    @Test
    public void testValidateGetAccountWhenAccountNameIsInvalid() {
        boolean response = validateGetAccount(DEFAULT_TENANT_ID, null, DEFAULT_ACCOUNT_PASSWORD);
        Assert.assertFalse("Should be FALSE", response);

        response = validateGetAccount(DEFAULT_TENANT_ID, "", DEFAULT_ACCOUNT_PASSWORD);
        Assert.assertFalse("Should be FALSE", response);
    }


    @Test
    public void testValidateGetAccountWhenAccountPasswordIsInvalid() {
        boolean response = validateGetAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_NAME, null);
        Assert.assertFalse("Should be FALSE", response);

        response = validateGetAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_NAME, "");
        Assert.assertFalse("Should be FALSE", response);

        response = validateGetAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_NAME, "123");
        Assert.assertFalse("Should be FALSE", response);
    }


    @Test
    public void testValidateCreateAccountWhenValid() {
        RoleModel roleFixture = new RoleModel(2, "No RoleModel");
        AccountModel accountFixture = AccountModel.builder()
                .havingPersonalDetails()
                    .withName(DEFAULT_ACCOUNT_NAME)
                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
                .havingRole()
                    .withRole(roleFixture)
                .build();

        boolean response = validateCreateAccount(DEFAULT_TENANT_ID, accountFixture);
        Assert.assertTrue("Should be TRUE", response);
    }


    @Test
    public void testValidateCreateAccountWhenAccountIsInvalid() {
        RoleModel roleFixture = new RoleModel(2, "No RoleModel");

        //name
        AccountModel accountFixture = AccountModel.builder()
                .havingPersonalDetails()
                    .withTenantId(DEFAULT_TENANT_ID)
                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
                    .withFlagActive(true)
                .havingRole()
                    .withRole(roleFixture)
                .build();

        boolean response = validateCreateAccount(DEFAULT_TENANT_ID, accountFixture);
        Assert.assertFalse("Should be FALSE", response);

        //email
        accountFixture = AccountModel.builder()
                .havingPersonalDetails()
                    .withTenantId(DEFAULT_TENANT_ID)
                    .withName(DEFAULT_ACCOUNT_NAME)
                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
                    .withFlagActive(true)
                .havingRole()
                    .withRole(roleFixture)
                .build();


        response = validateCreateAccount(DEFAULT_TENANT_ID, accountFixture);
        Assert.assertTrue("Should be TRUE", response);

        //password
        accountFixture = AccountModel.builder()
                .havingPersonalDetails()
                    .withTenantId(DEFAULT_TENANT_ID)
                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
                    .withName(DEFAULT_ACCOUNT_NAME)
                    .withFlagActive(true)
                .havingRole()
                    .withRole(roleFixture)
                .build();

        response = validateCreateAccount(DEFAULT_TENANT_ID, accountFixture);
        Assert.assertFalse("Should be FALSE", response);
    }


    @Test
    public void testValidateUpdateAccountWhenValid() {
        RoleModel roleFixture = new RoleModel(2, "No RoleModel");

        AccountModel accountFixture = AccountModel.builder()
                .havingPersonalDetails()
                    .withTenantId(DEFAULT_TENANT_ID)
                    .withId(DEFAULT_ACCOUNT_ID)
                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
                    .withName(DEFAULT_ACCOUNT_NAME)
                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
                    .withFlagActive(true)
                .havingRole()
                    .withRole(roleFixture)
                .build();

        boolean response = validateUpdateAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, accountFixture);
        Assert.assertTrue("Should be TRUE", response);
    }


    @Test
    public void testValidateUpdateAccountWhenTenantIsInvalid() {
        RoleModel roleFixture = new RoleModel(2, "No RoleModel");

        AccountModel accountFixture = AccountModel.builder()
                .havingPersonalDetails()
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ACCOUNT_ID)
                .withEmail(DEFAULT_ACCOUNT_EMAIL)
                .withName(DEFAULT_ACCOUNT_NAME)
                .withPassword(DEFAULT_ACCOUNT_PASSWORD)
                .withFlagActive(true)
                .havingRole()
                .withRole(roleFixture)
                .build();

        boolean response = validateUpdateAccount(null, DEFAULT_ACCOUNT_ID, accountFixture);
        Assert.assertFalse("Should be FALSE", response);
    }


    @Test
    public void testValidateUpdateAccountWhenAccountIdIsInvalid() {
        RoleModel roleFixture = new RoleModel(2, "No RoleModel");

        AccountModel accountFixture = AccountModel.builder()
                .havingPersonalDetails()
                    .withTenantId(DEFAULT_TENANT_ID)
                    .withId(DEFAULT_ACCOUNT_ID)
                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
                    .withName(DEFAULT_ACCOUNT_NAME)
                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
                    .withFlagActive(true)
                .havingRole()
                    .withRole(roleFixture)
                .build();

        boolean response = validateUpdateAccount(DEFAULT_TENANT_ID, 0, accountFixture);
        Assert.assertFalse("Should be FALSE", response);
    }


    @Test
    public void testValidateUpdateAccountWhenAccountIsInvalid() {
        RoleModel roleFixture = new RoleModel(2, "No RoleModel");

        //name
        AccountModel accountFixture = AccountModel.builder()
                .havingPersonalDetails()
                    .withTenantId(DEFAULT_TENANT_ID)
                    .withId(DEFAULT_ACCOUNT_ID)
                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
                    .withName("a")
                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
                    .withFlagActive(true)
                .havingRole()
                    .withRole(roleFixture)
                .build();

        boolean response = validateUpdateAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, accountFixture);
        Assert.assertFalse("Should be FALSE", response);

        //email
        accountFixture = AccountModel.builder()
                .havingPersonalDetails()
                    .withTenantId(DEFAULT_TENANT_ID)
                    .withId(DEFAULT_ACCOUNT_ID)
                    .withEmail("1")
                    .withName(DEFAULT_ACCOUNT_NAME)
                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
                    .withFlagActive(true)
                .havingRole()
                    .withRole(roleFixture)
                .build();

        response = validateUpdateAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, accountFixture);
        Assert.assertFalse("Should be FALSE", response);

        //password
        accountFixture = AccountModel.builder()
                .havingPersonalDetails()
                    .withTenantId(DEFAULT_TENANT_ID)
                    .withId(DEFAULT_ACCOUNT_ID)
                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
                    .withName(DEFAULT_ACCOUNT_NAME)
                    .withPassword("123")
                    .withFlagActive(true)
                .havingRole()
                    .withRole(roleFixture)
                .build();

        response = validateUpdateAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, accountFixture);
        Assert.assertFalse("Should be FALSE", response);
    }

}
