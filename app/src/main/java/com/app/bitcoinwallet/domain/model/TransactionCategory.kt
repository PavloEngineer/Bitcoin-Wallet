package com.app.bitcoinwallet.domain.model

enum class TransactionCategory(val value: String) {
    GROCERIES("groceries"),
    TAXI("taxi"),
    ELECTRONICS("electronics"),
    RESTAURANT("restaurant"),
    OTHER("other")
}