package com.example.account.api.rest.exception

class AccountException(var code: ExceptionCode): Exception(code.message)

enum class ExceptionCode(val message: String) {
    UNKNOWN("Unknown"),
    BAD_REQUEST("Bad request"),
    ACCOUNT_NOT_FOUND("Account not found"),
    ACCOUNT_EXISTS("AccountModel already exists"),
    ACCOUNT_NAME_EXISTS("AccountModel name already exists"),
    ROLE_NOT_FOUND("RoleModel not found");
}