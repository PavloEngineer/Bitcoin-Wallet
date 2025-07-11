package com.app.bitcoinwallet.ui.utils

import com.app.bitcoinwallet.domain.model.TransactionData
import com.app.bitcoinwallet.domain.utils.DatePatternConstants.DD_MM_YYYY
import com.app.bitcoinwallet.ui.model.TransactionGroupUI
import java.time.format.DateTimeFormatter

object TransactionGroupManager {

    fun mergeGroupedTransactions(
        oldList: List<TransactionGroupUI>,
        newList: List<TransactionGroupUI>
    ): List<TransactionGroupUI> {
        val mergedMap = LinkedHashMap<String, MutableList<TransactionData>>()

        for (group in oldList) {
            mergedMap[group.date] = group.items.toMutableList()
        }

        for (group in newList) {
            val existing = mergedMap[group.date]
            if (existing != null) {
                existing.addAll(group.items)
            } else {
                mergedMap[group.date] = group.items.toMutableList()
            }
        }

        return mergedMap
            .toSortedMap(compareByDescending { it })
            .map { (date, transactions) ->
                TransactionGroupUI(date, transactions.sortedByDescending { it.date })
            }
    }

    fun groupTransactionsByDate(transactions: List<TransactionData>): List<TransactionGroupUI> {
        return transactions
            .groupBy { transaction ->
                transaction.date.format(DateTimeFormatter.ofPattern(DD_MM_YYYY))
            }
            .toSortedMap(compareByDescending { it })
            .map { (date, items) ->
                TransactionGroupUI(date, items)
            }
    }
}