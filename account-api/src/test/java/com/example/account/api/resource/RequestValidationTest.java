package com.example.account.api.resource;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.example.account.api.resource.RequestValidation.validateGetAccount;

class RequestValidationTest {

    private static final String DEFAULT_ACCOUNT_ID = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
    private static final String DEFAULT_ACCOUNT_NAME = "name.surname";
    private static final String DEFAULT_ACCOUNT_PASSWORD = "secret";
    private static final String DEFAULT_ACCOUNT_EMAIL = "not.set@domain.com";

    @Test
    void testValidateGetAccountWhenValid() {
        boolean response = validateGetAccount(DEFAULT_ACCOUNT_NAME, DEFAULT_ACCOUNT_PASSWORD);
        Assertions.assertTrue(response, "Should be TRUE");
    }

    @Test
    void testValidateGetAccountWhenAccountNameIsInvalid() {
        boolean response = validateGetAccount(null, DEFAULT_ACCOUNT_PASSWORD);
        Assertions.assertFalse(response, "Should be FALSE");

        response = validateGetAccount("", DEFAULT_ACCOUNT_PASSWORD);
        Assertions.assertFalse(response, "Should be FALSE");
    }


    @Test
    void testValidateGetAccountWhenAccountPasswordIsInvalid() {
        boolean response = validateGetAccount(DEFAULT_ACCOUNT_NAME, null);
        Assertions.assertFalse(response, "Should be FALSE");

        response = validateGetAccount(DEFAULT_ACCOUNT_NAME, "");
        Assertions.assertFalse(response, "Should be FALSE");

        response = validateGetAccount(DEFAULT_ACCOUNT_NAME, "123");
        Assertions.assertFalse(response, "Should be FALSE");
    }


//    @Test
//    void testValidateCreateAccountWhenValid() {
//        RoleModel roleFixture = new RoleModel(2, "No RoleModel");
//        AccountModel accountFixture = AccountModel.builder()
//                .havingPersonalDetails()
//                    .withName(DEFAULT_ACCOUNT_NAME)
//                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
//                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
//                .build();
//
//        boolean response = validateCreateAccount(DEFAULT_TENANT_ID, accountFixture);
//        Assertions.assertTrue(response, "Should be TRUE");
//    }


//    @Test
//    void testValidateCreateAccountWhenAccountIsInvalid() {
//        RoleModel roleFixture = new RoleModel(2, "No RoleModel");
//
//        //name
//        AccountModel accountFixture = AccountModel.builder()
//                .havingPersonalDetails()
//                    .withTenantId(DEFAULT_TENANT_ID)
//                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
//                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
//                    .withFlagActive(true)
//                .havingRole()
//                    .withRole(roleFixture)
//                .build();
//
//        boolean response = validateCreateAccount(DEFAULT_TENANT_ID, accountFixture);
//        Assertions.assertFalse(response, "Should be FALSE");
//
//        //email
//        accountFixture = AccountModel.builder()
//                .havingPersonalDetails()
//                    .withTenantId(DEFAULT_TENANT_ID)
//                    .withName(DEFAULT_ACCOUNT_NAME)
//                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
//                    .withFlagActive(true)
//                .havingRole()
//                    .withRole(roleFixture)
//                .build();
//
//
//        response = validateCreateAccount(DEFAULT_TENANT_ID, accountFixture);
//        Assertions.assertTrue(response, "Should be TRUE");
//
//        //password
//        accountFixture = AccountModel.builder()
//                .havingPersonalDetails()
//                    .withTenantId(DEFAULT_TENANT_ID)
//                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
//                    .withName(DEFAULT_ACCOUNT_NAME)
//                    .withFlagActive(true)
//                .havingRole()
//                    .withRole(roleFixture)
//                .build();
//
//        response = validateCreateAccount(DEFAULT_TENANT_ID, accountFixture);
//        Assertions.assertFalse(response, "Should be FALSE");
//    }


//    @Test
//    void testValidateUpdateAccountWhenValid() {
//        RoleModel roleFixture = new RoleModel(2, "No RoleModel");
//
//        AccountModel accountFixture = AccountModel.builder()
//                .havingPersonalDetails()
//                    .withTenantId(DEFAULT_TENANT_ID)
//                    .withId(DEFAULT_ACCOUNT_ID)
//                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
//                    .withName(DEFAULT_ACCOUNT_NAME)
//                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
//                    .withFlagActive(true)
//                .havingRole()
//                    .withRole(roleFixture)
//                .build();
//
//        boolean response = validateUpdateAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, accountFixture);
//        Assertions.assertTrue(response, "Should be TRUE");
//    }


//    @Test
//    void testValidateUpdateAccountWhenTenantIsInvalid() {
//        RoleModel roleFixture = new RoleModel(2, "No RoleModel");
//
//        AccountModel accountFixture = AccountModel.builder()
//                .havingPersonalDetails()
//                .withTenantId(DEFAULT_TENANT_ID)
//                .withId(DEFAULT_ACCOUNT_ID)
//                .withEmail(DEFAULT_ACCOUNT_EMAIL)
//                .withName(DEFAULT_ACCOUNT_NAME)
//                .withPassword(DEFAULT_ACCOUNT_PASSWORD)
//                .withFlagActive(true)
//                .havingRole()
//                .withRole(roleFixture)
//                .build();
//
//        boolean response = validateUpdateAccount(null, DEFAULT_ACCOUNT_ID, accountFixture);
//        Assertions.assertFalse(response, "Should be FALSE");
//    }


//    @Test
//    void testValidateUpdateAccountWhenAccountIdIsInvalid() {
//        RoleModel roleFixture = new RoleModel(2, "No RoleModel");
//
//        AccountModel accountFixture = AccountModel.builder()
//                .havingPersonalDetails()
//                    .withTenantId(DEFAULT_TENANT_ID)
//                    .withId(DEFAULT_ACCOUNT_ID)
//                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
//                    .withName(DEFAULT_ACCOUNT_NAME)
//                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
//                    .withFlagActive(true)
//                .havingRole()
//                    .withRole(roleFixture)
//                .build();
//
//        boolean response = validateUpdateAccount(DEFAULT_TENANT_ID, 0, accountFixture);
//        Assertions.assertFalse(response, "Should be FALSE");
//    }


//    @Test
//    void testValidateUpdateAccountWhenAccountIsInvalid() {
//        RoleModel roleFixture = new RoleModel(2, "No RoleModel");
//
//        //name
//        AccountModel accountFixture = AccountModel.builder()
//                .havingPersonalDetails()
//                    .withTenantId(DEFAULT_TENANT_ID)
//                    .withId(DEFAULT_ACCOUNT_ID)
//                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
//                    .withName("a")
//                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
//                    .withFlagActive(true)
//                .havingRole()
//                    .withRole(roleFixture)
//                .build();
//
//        boolean response = validateUpdateAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, accountFixture);
//        Assertions.assertFalse(response, "Should be FALSE");
//
//        //email
//        accountFixture = AccountModel.builder()
//                .havingPersonalDetails()
//                    .withTenantId(DEFAULT_TENANT_ID)
//                    .withId(DEFAULT_ACCOUNT_ID)
//                    .withEmail("1")
//                    .withName(DEFAULT_ACCOUNT_NAME)
//                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
//                    .withFlagActive(true)
//                .havingRole()
//                    .withRole(roleFixture)
//                .build();
//
//        response = validateUpdateAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, accountFixture);
//        Assertions.assertFalse(response, "Should be FALSE");
//
//        //password
//        accountFixture = AccountModel.builder()
//                .havingPersonalDetails()
//                    .withTenantId(DEFAULT_TENANT_ID)
//                    .withId(DEFAULT_ACCOUNT_ID)
//                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
//                    .withName(DEFAULT_ACCOUNT_NAME)
//                    .withPassword("123")
//                    .withFlagActive(true)
//                .havingRole()
//                    .withRole(roleFixture)
//                .build();
//
//        response = validateUpdateAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, accountFixture);
//        Assertions.assertFalse(response, "Should be FALSE");
//    }

}
