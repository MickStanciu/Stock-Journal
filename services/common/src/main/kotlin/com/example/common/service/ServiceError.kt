package com.example.common.service

import com.example.common.repository.DataAccessError

sealed class ServiceError {
    class DataAccessError(val message: String = ""): ServiceError()
    class RecordNotFound(val message: String = ""): ServiceError()
}

fun toServiceError(repositoryError: DataAccessError): ServiceError {
    return when (repositoryError) {
        is DataAccessError.RecordNotFound -> ServiceError.RecordNotFound(repositoryError.message)
        is DataAccessError.DatabaseAccessError -> ServiceError.DataAccessError(repositoryError.message)
    }
}