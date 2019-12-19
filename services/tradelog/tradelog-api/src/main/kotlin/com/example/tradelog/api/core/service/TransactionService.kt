package com.example.tradelog.api.core.service

import com.example.tradelog.api.db.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionService(private val transactionRepository: TransactionRepository) {

    fun getAllTradedSymbols(accountId: String): List<String> {
        return transactionRepository.getUniqueSymbols(accountId)
    }

}