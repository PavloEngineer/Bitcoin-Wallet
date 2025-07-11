package com.app.bitcoinwallet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.bitcoinwallet.ui.screen.add_transaction.AddTransactionScreen
import com.app.bitcoinwallet.ui.screen.menu.MenuScreen

@Composable
fun AppNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MenuScreen.route
    ) {
        composable(
            route = Screen.MenuScreen.route
        ) {
            MenuScreen(navHostController)
        }

        composable(
            route = Screen.AddTransactionScreen.route
        ) {
            AddTransactionScreen(navHostController)
        }
    }
}