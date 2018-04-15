package com.example.account.validation;

import com.example.account.model.Account;
import com.example.account.model.Role;
import com.example.common.validator.FieldValidator;
import com.example.common.validator.StringValidator;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestValidation extends FieldValidator {

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
                .sizeGreaterOrEqualTo(5)
                .isValid();
    }

    private static boolean accountEmail(String email) {
        boolean response = new StringValidator(email)
                .notNull()
                .sizeLessOrEqualTo(64)
                .isValid();
        if (!response) {
            return false;
        }

        Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9_+.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        Matcher regMatcher   = regexPattern.matcher(email);
        return regMatcher.matches();
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

    public static boolean validateUpdateAccount(String tenantId, BigInteger accountId, Account account) {
        boolean response =  RequestValidation.tenantId(tenantId) && RequestValidation.accountId(accountId)
                && RequestValidation.account(account);

        //validate optional fields
        if (account.getName() != null) {
            response = response && RequestValidation.accountName(account.getName());
        }

        if (account.getPassword() != null) {
            response = response && RequestValidation.accountPassword(account.getPassword());
        }

        if (account.getEmail() != null) {
            response = response && RequestValidation.accountEmail(account.getEmail());
        }

        if (account.getRole() != null) {
            response = response && RequestValidation.role(account.getRole());
        }

        return response;
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

    public static Boolean validateGetAccount(String tenantId, BigInteger accountId) {
        return RequestValidation.tenantId(tenantId) && RequestValidation.accountId(accountId);
    }

}
