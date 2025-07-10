package com.app.bitcoinwallet.data.repository

import com.app.bitcoinwallet.data.source.network.CurrencyApi
import com.app.bitcoinwallet.domain.model.BitcoinData
import com.app.bitcoinwallet.domain.model.NetworkResult
import com.app.bitcoinwallet.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyApi: CurrencyApi
) : CurrencyRepository {

    override suspend fun getRateDollarsToBitcoin(
        ids: String,
        codeCurrencies: String
    ): NetworkResult<BitcoinData> {
        return try {
            val rateToBitcoinResponse = currencyApi.getRateDollarsToBitcoin(ids, codeCurrencies)
            if (rateToBitcoinResponse.isSuccessful) {
                NetworkResult.Success(rateToBitcoinResponse.body()?.bitcoinData ?: BitcoinData())
            } else {
                NetworkResult.Error(rateToBitcoinResponse.errorBody()?.string() ?: "")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "")
        }
    }
}