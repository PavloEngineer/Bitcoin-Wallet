package com.app.bitcoinwallet.domain.usecase

import com.app.bitcoinwallet.domain.model.BitcoinData
import com.app.bitcoinwallet.domain.model.NetworkResult
import com.app.bitcoinwallet.domain.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRateDollarsToBitcoinUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(ids: String, codeCurrencies: String): NetworkResult<BitcoinData> {
        return withContext(Dispatchers.IO) {
            currencyRepository.getRateDollarsToBitcoin(ids, codeCurrencies)
        }
    }
}