package com.example.gateway.api.core.model

class TokenClaims(val tenantId: String,
                  val accountId: Int,
                  val roleId: Int,
                  val issuer: String)
