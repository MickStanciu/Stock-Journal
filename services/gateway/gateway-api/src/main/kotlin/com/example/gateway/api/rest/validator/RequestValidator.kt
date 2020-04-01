package com.example.gateway.api.rest.validator

import com.example.common.validator.FieldValidator
import com.example.common.validator.StringValidator
import java.util.function.Predicate

class RequestValidator: FieldValidator() {

    companion object {
        private const val USERNAME_PATTERN = "(?:[a-zA-Z]){3,}(?:[0-9])*"

        //Rule: at least 1 funny chars, 1 number, 5 letters
        private val CONTAINS_FUNNY_CHAR = arrayOf('~', '!', '@', '#', '$', '%', '^', '&', '*', '~', '-', '=')


        fun validateAuthenticate(username: String, password: String): Boolean {
            return testUsername.test(username)
                    && testPassword.test(password)
//                    && analysePassword(password)
        }

        private var testUsername = Predicate { s: String ->
            StringValidator(s)
                    .sizeGreaterOrEqualTo(5)
                    .sizeLessOrEqualTo(20)
                    .regex(USERNAME_PATTERN)
                    .isValid
        }

        private var testPassword = Predicate { s: String -> StringValidator(s)
                .sizeGreaterOrEqualTo(5)
                .sizeLessOrEqualTo(50)
                .isValid
        }

        private fun analysePassword(text: String): Boolean {
            var funnyChars = 0
            var numbers = 0
            var lowerCaseLetters = 0
            var upperCaseLetters = 0

            text.toCharArray().forEach { ch ->
                if (CONTAINS_FUNNY_CHAR.contains(ch)) {
                    funnyChars += 1
                }

                val numeric = ch.toLong()
                if (numeric in 64..90) {
                    upperCaseLetters += 1
                }

                if (numeric in 97..122) {
                    lowerCaseLetters += 1
                }

                if (numeric in 48..57) {
                    numbers += 1
                }
            }
            return funnyChars >= 1 && numbers >= 1 && lowerCaseLetters >= 1 && upperCaseLetters >= 1
        }

    }
}