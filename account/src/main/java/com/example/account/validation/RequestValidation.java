package com.example.account.validation;

import com.example.account.model.Account;
import com.example.account.model.Role;
import com.example.common.validator.IntegerValidator;
import com.example.common.validator.StringValidator;

public class RequestValidation {

    private static boolean tenantId(String tenantId) {
        return new StringValidator(tenantId)
                .notNull()
                .sizeEqualTo(36)
                .isValid();
    }

    private static boolean accountId(Integer accountId) {
        return new IntegerValidator(accountId)
                .notNull()
                .greaterThanZero()
                .isValid();
    }



    private static boolean accountName(String name) {
        return new StringValidator(name)
                .notNull()
                .sizeLessOrEqualTo(64)
                .sizeGreaterOrEqualTo(5)
                .isValid();
    }

    private static boolean accountPassword(String password) {
        return new StringValidator(password)
                .notNull()
                .sizeLessOrEqualTo(64)
                .isValid();
    }

    private static boolean accountEmail(String email) {
        return new StringValidator(email)
                .notNull()
                .sizeLessOrEqualTo(64)
                .isValid();
    }

    private static boolean role(Role role) {
        return role != null && role.getId() != null && role.getId() >= 1 && role.getId() <= 5;
    }

    private static boolean accountActive(Boolean active) {
        return active != null;
    }

    private static boolean account(Account account) {
        return account != null;
    }

    public static boolean validateUpdateAccount(String tenantId, Integer accountId, Account account) {
        return RequestValidation.tenantId(tenantId) && RequestValidation.accountId(accountId)
                && RequestValidation.account(account)
                && RequestValidation.accountName(account.getName())
                && RequestValidation.accountPassword(account.getPassword())
                && RequestValidation.accountEmail(account.getEmail())
                && RequestValidation.accountActive(account.isActive())
                && RequestValidation.role(account.getRole());
    }

    public static boolean validateCreateAccount(String tenantId, Account account) {
        return RequestValidation.tenantId(tenantId) && RequestValidation.account(account)
                && RequestValidation.accountName(account.getName())
                && RequestValidation.accountPassword(account.getPassword());
    }


    public static Boolean validateGetAccount(String tenantId, String name, String password) {
        return RequestValidation.tenantId(tenantId) && RequestValidation.accountName(name)
                && RequestValidation.accountPassword(password);
    }

}
