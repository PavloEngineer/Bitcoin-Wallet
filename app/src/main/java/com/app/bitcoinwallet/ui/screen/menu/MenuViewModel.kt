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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
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

    private var currentPage = 0
    private val pageSize = 20

    private var isLastPage = false
    private var isLoadingTransactions = false

    fun loadTransactions() {
        if (isLoadingTransactions || isLastPage) return

        isLoadingTransactions = true
        _transactionsState.update { StateUI.Loading }

        viewModelScope.launch {
            val offset = currentPage * pageSize
            getTransactionDataUseCase(pageSize, offset)
                .catch { e ->
                    isLoadingTransactions = false
                    _transactionsState.update {
                        StateUI.Error(
                            e.message ?: context.getString(R.string.error_unknown)
                        )
                    }
                }
                .collect { newList ->
                    isLoadingTransactions = false
                    if (newList.size < pageSize) isLastPage = true
                    currentPage++

                    val currentList = (_transactionsState.value as? StateUI.Success)?.data
                    currentList?.let { list ->
                        val updatedTransactionsList = withContext(Dispatchers.Default) {
                            mergeGroupedTransactions(list, groupTransactionsByDate(newList))
                        }

                        _transactionsState.update { StateUI.Success(updatedTransactionsList) }
                    }
                }
        }
    }

    fun loadCurrentRateBitcoin() {
        viewModelScope.launch {
            _currentRateBitcoinState.update { StateUI.Loading }

            when (val result = getRateDollarsToBitcoinUseCase()) {
                is NetworkResult.Success -> {
                    _currentRateBitcoinState.update { StateUI.Success(result.data.usd) }
                }

                is NetworkResult.Error -> {
                    _currentRateBitcoinState.update { StateUI.Error(result.message) }
                }
            }
        }
    }

    fun loadCurrentBalance() {
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
            val newTransaction = TransactionData(
                date = LocalDate.now(),
                amountCoins = sum,
                isExpenses = false
            )
            insertTransactionDataUseCase(newTransaction)

            val currentBalance = walletDataStore.getBalance().first() ?: 0f
            walletDataStore.saveBalance(currentBalance + sum)
        }
    }
}