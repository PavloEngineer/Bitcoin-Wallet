package com.app.bitcoinwallet.domain.model


import java.time.LocalDateTime

data class TransactionData(
    val id: Int = 0,
    val date: LocalDateTime,
    val amountCoins: Float,
    val isExpenses: Boolean,
    val category: TransactionCategory? = null
)
