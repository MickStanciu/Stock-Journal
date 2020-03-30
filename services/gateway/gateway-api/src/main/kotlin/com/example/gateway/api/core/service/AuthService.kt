package com.example.gateway.api.core.service

import com.example.gateway.api.core.util.TokenUtil
import org.springframework.stereotype.Service

@Service
class AuthService {
    fun generateToken(id: String): String {
        return TokenUtil.generateToken(id)
    }
}
