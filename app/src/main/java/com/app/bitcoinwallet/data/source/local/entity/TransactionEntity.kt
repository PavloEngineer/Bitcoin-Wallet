package com.app.bitcoinwallet.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.bitcoinwallet.domain.model.TransactionCategory
import com.app.bitcoinwallet.domain.model.TransactionData
import java.time.LocalDate

@Entity("transaction")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: LocalDate,
    val amountCoins: Float,
    val isExpenses: Boolean,
    val category: TransactionCategory?
) {
    fun toTransactionData(): TransactionData = TransactionData(
        id,
        date,
        amountCoins,
        isExpenses,
        category
    )

    companion object {
        fun toTransactionEntity(transactionData: TransactionData): TransactionEntity = TransactionEntity(
            transactionData.id,
            transactionData.date,
            transactionData.amountCoins,
            transactionData.isExpenses,
            transactionData.category
        )
    }
}
