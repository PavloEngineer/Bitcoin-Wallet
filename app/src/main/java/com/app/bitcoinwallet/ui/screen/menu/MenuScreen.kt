package com.app.bitcoinwallet.ui.screen.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.app.bitcoinwallet.ui.navigation.Screen

@Composable
fun MenuScreen(
    navController: NavController
) {
    val menuViewModel: MenuViewModel = hiltViewModel()
    val balanceState by menuViewModel.balanceState.collectAsState()
    val transactionsState by menuViewModel.transactionsState.collectAsState()
    val currentRateBitcoinState by menuViewModel.currentRateBitcoinState.collectAsState()
    val isLoadingNextPage by menuViewModel.isLoadingNextPage.collectAsState()

    LaunchedEffect(Unit) {
        menuViewModel.loadAllData()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                menuViewModel.loadAllData()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    MenuContent(
        balanceState = balanceState,
        transactionsState = transactionsState,
        currentRateBitcoinState = currentRateBitcoinState,
        onLoadNextPage = {
            menuViewModel.loadMoreTransactions()
        },
        onAddCoinsToWallet = {
            menuViewModel.addCoinsToBalance(it)
        },
        onAddTransactionButtonClick = {
            navController.navigate(Screen.AddTransactionScreen.route)
        },
        isLoadingNextPage = isLoadingNextPage
    )
}