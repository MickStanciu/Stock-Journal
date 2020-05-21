package com.example.common.repository

sealed class DataAccessError {
    class RecordNotFound(val message: String = "Record not found"): DataAccessError()
    class DatabaseAccessError(val message: String = "Something went wrong in DAO"): DataAccessError()
    class InvalidRecordFormat(val message: String = "Record is invalid"): DataAccessError()
}