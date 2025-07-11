package com.app.bitcoinwallet.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary =  Pink60,
    secondary = PurpleGrey70,
    tertiary = PurpleGrey40,
    background = MixedBackgroundBlue
)

private val LightColorScheme = lightColorScheme(
    primary =  Pink60,
    secondary = PurpleGrey70,
    tertiary = PurpleGrey40,
    background = MixedBackgroundBlue
)

@Composable
fun BitcoinWalletTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}