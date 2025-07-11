package com.app.bitcoinwallet.ui.screen.menu

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bitcoinwallet.R
import com.app.bitcoinwallet.domain.model.NetworkResult
import com.app.bitcoinwallet.domain.model.TransactionData
import com.app.bitcoinwallet.domain.usecase.GetRateDollarsToBitcoinUseCase
import com.app.bitcoinwallet.domain.usecase.GetTransactionsByPageUseCase
import com.app.bitcoinwallet.domain.usecase.InsertTransactionDataUseCase
import com.app.bitcoinwallet.domain.utils.WalletDataStore
import com.app.bitcoinwallet.ui.model.StateUI
import com.app.bitcoinwallet.ui.model.TransactionGroupUI
import com.app.bitcoinwallet.ui.utils.TransactionGroupManager.groupTransactionsByDate
import com.app.bitcoinwallet.ui.utils.TransactionGroupManager.mergeGroupedTransactions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val walletDataStore: WalletDataStore,
    private val insertTransactionDataUseCase: InsertTransactionDataUseCase,
    private val getTransactionDataUseCase: GetTransactionsByPageUseCase,
    private val getRateDollarsToBitcoinUseCase: GetRateDollarsToBitcoinUseCase,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _balanceState = MutableStateFlow<StateUI<Float>>(StateUI.Loading)
    val balanceState: StateFlow<StateUI<Float>> = _balanceState

    private val _transactionsState =
        MutableStateFlow<StateUI<List<TransactionGroupUI>>>(StateUI.Loading)
    val transactionsState: StateFlow<StateUI<List<TransactionGroupUI>>> = _transactionsState

    private val _currentRateBitcoinState =
        MutableStateFlow<StateUI<Int>>(StateUI.Loading)
    val currentRateBitcoinState: StateFlow<StateUI<Int>> =
        _currentRateBitcoinState

    private val _isLoadingNextPage = MutableStateFlow(false)
    val isLoadingNextPage: StateFlow<Boolean> = _isLoadingNextPage

    private var currentPage = 0
    private val pageSize = 20

    private var isLastPage = false

    fun loadAllData() {
        loadMoreTransactions()
        updateTransactionsCollecting()
        loadCurrentBalance()
        loadCurrentRateBitcoin()
    }

    fun loadMoreTransactions() {
        if (isLoadingNextPage.value || isLastPage) return

        viewModelScope.launch {
            _isLoadingNextPage.update { true }

            val result = getTransactionDataUseCase(pageSize, currentPage * pageSize).first()

            if (result.isNotEmpty()) {
                if (result.size == pageSize) {
                    currentPage++
                } else {
                    isLastPage = true
                }

                updateTransactionsState(result)
            }

            _isLoadingNextPage.update { false }
        }
    }

    private fun updateTransactionsState(newTransactions: List<TransactionData>) {
        val currentList = (_transactionsState.value as? StateUI.Success)?.data ?: emptyList()
        val updatedList =
            mergeGroupedTransactions(currentList, groupTransactionsByDate(newTransactions))
        _transactionsState.update { StateUI.Success(updatedList) }
    }

    private fun loadCurrentRateBitcoin() {
        viewModelScope.launch {
            val lastCall = walletDataStore.getLastBitcoinRateCallTimestamp().first()?: 0L
            val currentTime = System.currentTimeMillis()
            val oneHourInMillis = 3600 * 1000L

            if ((currentTime - lastCall) < oneHourInMillis) {
                val cachedBitcoinRate = walletDataStore.getBitcoinRateUsd().first()?:0
                _currentRateBitcoinState.update { StateUI.Success(cachedBitcoinRate) }
                return@launch
            }

            _currentRateBitcoinState.update { StateUI.Loading }

            when (val result = getRateDollarsToBitcoinUseCase()) {
                is NetworkResult.Success -> {
                    walletDataStore.saveLastBitcoinRateCallTimestamp(currentTime)
                    walletDataStore.saveBitcoinRateUsd(result.data.usd)
                    _currentRateBitcoinState.update { StateUI.Success(result.data.usd) }
                }
                is NetworkResult.Error -> {
                    _currentRateBitcoinState.update {
                        StateUI.Error(result.message)
                    }
                }
            }
        }
    }

    private fun loadCurrentBalance() {
        viewModelScope.launch {
            _balanceState.update { StateUI.Loading }
            walletDataStore.getBalance()
                .catch { e ->
                    _balanceState.update {
                        StateUI.Error(
                            e.message ?: context.getString(R.string.error_unknown)
                        )
                    }
                }
                .collect { balance ->
                    _balanceState.update { StateUI.Success(balance ?: 0f) }
                }
        }
    }

    fun addCoinsToBalance(sum: Float) {
        viewModelScope.launch {
            saveTransaction(sum)

            val currentBalance = walletDataStore.getBalance().first() ?: 0f
            walletDataStore.saveBalance(currentBalance + sum)
        }
    }

    private suspend fun saveTransaction(sum: Float) {
        val newTransaction = TransactionData(
            date = LocalDateTime.now(),
            amountCoins = sum,
            isExpenses = false
        )
        insertTransactionDataUseCase(newTransaction)
    }

    private fun updateTransactionsCollecting() {
        viewModelScope.launch {
            getTransactionDataUseCase(pageSize, currentPage * pageSize).collect {
                _isLoadingNextPage.update { true }
                updateTransactionsState(it)
                _isLoadingNextPage.update { false }
            }
        }
    }
}