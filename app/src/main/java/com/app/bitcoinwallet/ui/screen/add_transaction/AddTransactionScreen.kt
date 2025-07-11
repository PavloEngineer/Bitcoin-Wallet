package com.app.bitcoinwallet.ui.screen.add_transaction

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun AddTransactionScreen(
    navController: NavController
) {
    val viewModel: AddTransactionViewModel = hiltViewModel()
    val errorScreen by viewModel.errorScreenState.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(errorScreen) {
        if (errorScreen.isNotEmpty()) {
            Toast.makeText(context, errorScreen, Toast.LENGTH_SHORT).show()
        }
    }

    AddTransactionContent(
        onBackButtonClick = {
            navController.navigateUp()
        },
        onSaveTransactionButtonClick = {
            viewModel.saveTransaction(it)
            navController.navigateUp()
        }
    )
}