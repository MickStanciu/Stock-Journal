package com.example.account.api.rest.validator

import com.example.common.validator.FieldValidator
import com.example.common.validator.StringValidator

class RequestValidator: FieldValidator() {

    companion object {
        fun validateGetAccountByUserAndPassword(username: String, password: String): Boolean {
            return testUsername(username) && testPassword(password)
        }

        //TODO: add regex
        private var testUsername = { s: String ->
            StringValidator(s)
                    .sizeGreaterOrEqualTo(5)
                    .sizeLessOrEqualTo(20)
                    .isValid()
        }

        private var testPassword = { s: String ->
            StringValidator(s)
                .sizeGreaterOrEqualTo(5)
                .sizeLessOrEqualTo(20)
                .isValid()
        }

    }
}