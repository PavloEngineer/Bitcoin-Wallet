package com.app.bitcoinwallet.data.repository

import com.app.bitcoinwallet.data.source.local.dao.TransactionDao
import com.app.bitcoinwallet.data.source.local.entity.TransactionEntity
import com.app.bitcoinwallet.domain.model.TransactionData
import com.app.bitcoinwallet.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
): TransactionRepository {

    override suspend fun insertTransactionData(transactionData: TransactionData) {
        if (transactionData.amountCoins > 0) {
            transactionDao.insertTransactionEntity(
                TransactionEntity.toTransactionEntity(
                    transactionData
                )
            )
        }
    }

    override fun getTransactionsByPage(limit: Int, offset: Int): Flow<List<TransactionData>> =
        transactionDao.getTransactionsByPage(limit, offset)
            .map { list -> list.map { it.toTransactionData() } }
            .catch { e ->
                emit(emptyList())
            }
}