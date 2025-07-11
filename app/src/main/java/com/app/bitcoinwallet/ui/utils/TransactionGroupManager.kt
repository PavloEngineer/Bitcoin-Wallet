package com.app.bitcoinwallet.ui.utils

import com.app.bitcoinwallet.domain.model.TransactionData
import com.app.bitcoinwallet.domain.utils.DatePatternConstants.DAY_MONTH_YEAR
import com.app.bitcoinwallet.ui.model.TransactionGroupUI
import java.time.format.DateTimeFormatter

object TransactionGroupManager {

    fun mergeGroupedTransactions(
        oldList: List<TransactionGroupUI>,
        newList: List<TransactionGroupUI>
    ): List<TransactionGroupUI> {
        val mergedMap = LinkedHashMap<String, MutableList<TransactionData>>()

        (oldList + newList).forEach { group ->
            val existingTransactions = mergedMap.getOrPut(group.date) { mutableListOf() }

            group.items.forEach { newTransaction ->
                if (!existingTransactions.any { it.id == newTransaction.id }) {
                    existingTransactions.add(newTransaction)
                }
            }
        }

        return mergedMap
            .toSortedMap(compareByDescending { it })
            .map { (date, transactions) ->
                TransactionGroupUI(
                    date = date,
                    items = transactions
                        .distinctBy { it.id }
                        .sortedByDescending { it.date }
                )
            }
    }


    fun groupTransactionsByDate(transactions: List<TransactionData>): List<TransactionGroupUI> {
        return transactions
            .groupBy { transaction ->
                transaction.date.format(DateTimeFormatter.ofPattern(DAY_MONTH_YEAR))
            }
            .toSortedMap(compareByDescending { it })
            .map { (date, items) ->
                TransactionGroupUI(date, items)
            }
    }
}