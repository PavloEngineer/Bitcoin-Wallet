package com.app.bitcoinwallet.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.app.bitcoinwallet.ui.navigation.AppNavGraph
import com.app.bitcoinwallet.ui.theme.BitcoinWalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BitcoinWalletTheme {
                val colorStatusBar = MaterialTheme.colorScheme.background.toArgb()
                val navHostController = rememberNavController()
                LaunchedEffect(Unit) {
                    val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
                    windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
                    window.statusBarColor = colorStatusBar
                }

                AppNavGraph(navHostController)
            }
        }
    }
}
