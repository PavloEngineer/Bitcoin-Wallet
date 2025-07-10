package com.app.bitcoinwallet.data.utils

import androidx.room.TypeConverter
import com.app.bitcoinwallet.domain.model.TransactionCategory

object TransactionCategoryConverter {

    @TypeConverter
    fun fromCategory(category: TransactionCategory): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(value: String): TransactionCategory {
        return try {
            TransactionCategory.valueOf(value)
        } catch (e: Exception) {
            TransactionCategory.OTHER
        }
    }
}