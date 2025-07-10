package com.app.bitcoinwallet.domain.usecase

import com.app.bitcoinwallet.domain.model.TransactionData
import com.app.bitcoinwallet.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertTransactionDataUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(transactionData: TransactionData) {
        withContext(Dispatchers.IO) {
            transactionRepository.insertTransactionData(transactionData)
        }
    }
}