package com.example.common.error

sealed class DataAccessError {
    class RecordNotFound(val message: String = "Record not found"): DataAccessError()
}