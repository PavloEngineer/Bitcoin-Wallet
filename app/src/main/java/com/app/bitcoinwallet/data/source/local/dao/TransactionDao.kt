package com.app.bitcoinwallet.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.bitcoinwallet.data.source.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
   suspend fun insertTransactionEntity(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM `transaction` ORDER BY date DESC LIMIT :limit OFFSET :offset")
    fun getTransactionsByPage(limit: Int, offset: Int): Flow<List<TransactionEntity>>
}