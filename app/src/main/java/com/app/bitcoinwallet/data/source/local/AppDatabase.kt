package com.app.bitcoinwallet.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.bitcoinwallet.data.source.local.dao.TransactionDao
import com.app.bitcoinwallet.data.source.local.entity.TransactionEntity
import com.app.bitcoinwallet.data.utils.LocalDateConverter
import com.app.bitcoinwallet.data.utils.TransactionCategoryConverter

@Database(entities = [TransactionEntity::class], version = 1)
@TypeConverters(LocalDateConverter::class, TransactionCategoryConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getTransactionDao(): TransactionDao
}