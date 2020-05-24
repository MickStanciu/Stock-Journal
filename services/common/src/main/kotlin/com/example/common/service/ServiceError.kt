package com.example.common.service

sealed class ServiceError {
    class DataAccessError(val message: String = ""): ServiceError()
    class RecordNotFound(val message: String = ""): ServiceError()
}