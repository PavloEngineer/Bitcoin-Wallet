package com.app.bitcoinwallet.domain.repository

import com.app.bitcoinwallet.domain.model.TransactionData
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

   suspend fun insertTransactionData(transactionData: TransactionData)

   fun getTransactionsByPage(limit: Int, offset: Int): Flow<List<TransactionData>>
}