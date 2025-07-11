package com.app.bitcoinwallet.ui.model

import com.app.bitcoinwallet.domain.model.TransactionData

data class TransactionGroupUI(
    val date: String,
    val items: List<TransactionData>
)
