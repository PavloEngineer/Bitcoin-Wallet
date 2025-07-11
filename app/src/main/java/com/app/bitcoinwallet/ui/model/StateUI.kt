package com.app.bitcoinwallet.ui.model

sealed class StateUI<out T> {
    data object Loading : StateUI<Nothing>()
    data class Success<T>(val data: T): StateUI<T>()
    data class Error(val message: String): StateUI<Nothing>()
}
