package com.example.common.validator


open class FieldValidator {

    companion object {
        var symbol= { s: String ->
            StringValidator(s)
                    .notEmpty()
                    .sizeGreaterOrEqualTo(1)
                    .sizeLessOrEqualTo(6)
                    .isValid()
        }

        var limit = { n: Int ->
            IntegerValidator(n)
                    .greaterThanZero()
                    .isValid()
        }

        var UUID = { s: String ->
            StringValidator(s)
                    .sizeEqualTo(36)
                    .isValid()
        }
    }

}