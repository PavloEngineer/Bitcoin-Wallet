package com.app.bitcoinwallet.di

import com.app.bitcoinwallet.data.repository.CurrencyRepositoryImpl
import com.app.bitcoinwallet.data.repository.TransactionRepositoryImpl
import com.app.bitcoinwallet.domain.repository.CurrencyRepository
import com.app.bitcoinwallet.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindsTransactionRepository(
        catTransactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    fun bindsCurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl
    ): CurrencyRepository
}