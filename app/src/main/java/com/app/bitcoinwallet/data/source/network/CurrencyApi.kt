package com.app.bitcoinwallet.data.source.network

import com.app.bitcoinwallet.data.model.BitcoinResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("simple/price")
    suspend fun getRateDollarsToBitcoin(
        @Query("ids") ids: String,
        @Query("vs_currencies") codeCurrencies: String
    ): Response<BitcoinResponse>
}