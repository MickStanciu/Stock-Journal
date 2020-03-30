package com.example.gateway.api.rest.validator

import com.example.common.validator.FieldValidator
import com.example.common.validator.StringValidator
import java.util.function.Predicate

class RequestValidator: FieldValidator() {

    companion object {
        fun validateAuthenticate(username: String, password: String): Boolean {
            return testUsername.test(username) && testPassword.test(password)
        }

        //TODO: add regex
        private var testUsername = Predicate { s: String? ->
            StringValidator(s)
                    .notNull()
                    .sizeGreaterOrEqualTo(5)
                    .sizeLessOrEqualTo(20)
                    .isValid
        }

        private var testPassword = Predicate { s: String? -> StringValidator(s)
                .notNull()
                .sizeGreaterOrEqualTo(5)
                .sizeLessOrEqualTo(20)
                .isValid
        }

    }
}