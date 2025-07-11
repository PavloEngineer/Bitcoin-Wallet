package com.app.bitcoinwallet.ui.navigation

sealed class Screen(val route: String) {
    data object MenuScreen: Screen("menu_screen")
    data object TransactionCreatorScreen: Screen("transaction_creator_screen")
}