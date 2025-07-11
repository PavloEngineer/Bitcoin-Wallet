package com.app.bitcoinwallet.ui.screen.add_transaction

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bitcoinwallet.R
import com.app.bitcoinwallet.domain.model.TransactionData
import com.app.bitcoinwallet.domain.usecase.InsertTransactionDataUseCase
import com.app.bitcoinwallet.domain.utils.WalletDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val insertTransactionDataUseCase: InsertTransactionDataUseCase,
    private val walletDataStore: WalletDataStore,
    @ApplicationContext val context: Context
): ViewModel() {

    private val _errorScreenState =
        MutableStateFlow("")
    val errorScreenState: StateFlow<String> = _errorScreenState

    fun saveTransaction(transactionData: TransactionData) {
        viewModelScope.launch {
            val currentBalance = walletDataStore.getBalance().first()?:0f

            if (transactionData.amountCoins <= currentBalance) {
                insertTransactionDataUseCase(transactionData)
                walletDataStore.saveBalance(currentBalance - transactionData.amountCoins)
            } else {
                _errorScreenState.update { context.getString(R.string.error_small_balance) }
            }
        }
    }
}