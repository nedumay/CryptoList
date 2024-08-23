package com.example.cryptolist.di

import android.app.Application
import com.example.cryptolist.data.database.AppDatabase
import com.example.cryptolist.data.database.CoinInfoDao
import com.example.cryptolist.data.network.ApiFactory
import com.example.cryptolist.data.network.ApiService
import com.example.cryptolist.data.repository.CoinRepositoryImpl
import com.example.cryptolist.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object{

        @Provides
        @ApplicationScope
        fun provideCoinInfoDao(
            application: Application
        ) : CoinInfoDao{
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideApiService():ApiService{
            return ApiFactory.apiService
        }

    }
}