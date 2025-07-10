package com.app.bitcoinwallet.domain.usecase

import com.app.bitcoinwallet.domain.model.TransactionData
import com.app.bitcoinwallet.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTransactionsByPageUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(limit: Int, offset: Int): Flow<List<TransactionData>> {
       return withContext(Dispatchers.IO) {
            transactionRepository.getTransactionsByPage(limit, offset)
        }
    }
}