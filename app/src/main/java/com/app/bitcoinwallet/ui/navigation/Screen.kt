package com.app.bitcoinwallet.ui.navigation

sealed class Screen(val route: String) {
    data object MenuScreen: Screen("menu_screen")
    data object AddTransactionScreen: Screen("add_transaction_screen")
}