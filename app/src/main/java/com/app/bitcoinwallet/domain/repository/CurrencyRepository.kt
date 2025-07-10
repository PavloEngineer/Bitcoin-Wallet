package com.app.bitcoinwallet.domain.repository

import com.app.bitcoinwallet.domain.model.BitcoinData
import com.app.bitcoinwallet.domain.model.NetworkResult

interface CurrencyRepository {

    suspend fun getRateDollarsToBitcoin(ids: String, codeCurrencies: String): NetworkResult<BitcoinData>
}