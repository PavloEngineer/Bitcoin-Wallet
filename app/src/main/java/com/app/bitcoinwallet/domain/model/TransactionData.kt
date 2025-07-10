package com.app.bitcoinwallet.domain.model

import java.time.LocalDate

data class TransactionData(
    val id: Int,
    val date: LocalDate,
    val amountCoins: Float,
    val isExpenses: Boolean,
    val category: TransactionCategory
)
