package com.app.bitcoinwallet.data.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.bitcoinwallet.domain.utils.WalletDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "wallet_prefs")
@Singleton
class WalletDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
): WalletDataStore {

    companion object {
        private val KEY_BITCOIN_RATE_DATE_UPDATE = longPreferencesKey("bitcoin_rate_date_update")
        private val KEY_BALANCE_COINS = floatPreferencesKey("balance_in_coins")
        private val KEY_BITCOIN_RATE_USD = intPreferencesKey("bitcoin_rate_usd")
    }

    override suspend fun saveLastBitcoinRateCallTimestamp(timestamp: Long) {
        context.dataStore.edit { prefs ->
            prefs[KEY_BITCOIN_RATE_DATE_UPDATE] = timestamp
        }
    }

    override fun getLastBitcoinRateCallTimestamp(): Flow<Long?> =
        context.dataStore.data.map { prefs ->
            prefs[KEY_BITCOIN_RATE_DATE_UPDATE]
        }

    override suspend fun saveBalance(coins: Float) {
        context.dataStore.edit { prefs ->
            prefs[KEY_BALANCE_COINS] = coins
        }
    }

    override fun getBalance(): Flow<Float?> =
        context.dataStore.data.map { prefs ->
            prefs[KEY_BALANCE_COINS]
        }

    override suspend fun saveBitcoinRateUsd(rate: Int) {
        context.dataStore.edit { prefs ->
            prefs[KEY_BITCOIN_RATE_USD] = rate
        }
    }

    override fun getBitcoinRateUsd(): Flow<Int?> =
        context.dataStore.data.map { prefs ->
            prefs[KEY_BITCOIN_RATE_USD]
        }
}