package com.app.bitcoinwallet.data.model

import com.app.bitcoinwallet.domain.model.BitcoinData
import com.google.gson.annotations.SerializedName

data class BitcoinResponse(

    @SerializedName("bitcoin")
    val bitcoinData: BitcoinData
)