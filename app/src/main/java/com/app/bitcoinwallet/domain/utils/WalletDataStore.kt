package com.app.bitcoinwallet.domain.utils

import kotlinx.coroutines.flow.Flow

interface WalletDataStore {

    suspend fun saveLastBitcoinRateCallTimestamp(timestamp: Long)

    fun getLastBitcoinRateCallTimestamp(): Flow<Long?>

    suspend fun saveBalance(coins: Float)

    fun getBalance(): Flow<Float?>

    suspend fun saveBitcoinRateUsd(rate: Int)

    fun getBitcoinRateUsd(): Flow<Int?>
}