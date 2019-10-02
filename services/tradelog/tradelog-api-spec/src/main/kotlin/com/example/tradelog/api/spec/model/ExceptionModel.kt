package com.example.tradelog.api.spec.model

import com.example.tradelog.api.spec.exception.ExceptionCode
import java.time.LocalDateTime

class ExceptionModel(_code: ExceptionCode, _message: String, _details: String) {
    private val code: ExceptionCode = _code
    private val message: String = _message
    private val details: String = _details
    private val timestamp: LocalDateTime = LocalDateTime.now()
}