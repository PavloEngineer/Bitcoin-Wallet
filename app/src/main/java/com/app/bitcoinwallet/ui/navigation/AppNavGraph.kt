package com.app.bitcoinwallet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MenuScreen.route
    ) {

    }

    NavHost(
        navController = navHostController,
        startDestination = Screen.TransactionCreatorScreen.route
    ) {

    }
}