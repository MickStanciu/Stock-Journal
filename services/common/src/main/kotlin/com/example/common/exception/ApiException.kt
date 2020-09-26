package com.example.common.exception

class ApiException(val code: ApiExceptionCode, private val customMessage: String? = null): Exception() {
    override val message: String
        get() = customMessage ?: code.defaultMessage
}

enum class ApiExceptionCode(val defaultMessage: String = "") {

    // Database related
//    RECORD_NOT_FOUND("We could not find the specified record"),
//    DATABASE_ACCESS_ERROR("Unknown Database Error"),

    // Data quality related
    VALIDATION_ERROR("Model has invalid data"),

    // Core related
    USER_NOT_FOUND("User not found"),

    // 3rd Party API related
    EXTERNAL_API_OTHER("External API returned an error"),
}