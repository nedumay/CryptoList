package com.example.cryptolist.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.cryptolist.data.database.CoinInfoDao
import com.example.cryptolist.data.mapper.CoinMapper
import com.example.cryptolist.data.network.ApiService
import javax.inject.Inject

class RefreshDataWorkerFactory @Inject constructor(
    private val mapper: CoinMapper,
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return RefreshDataWorker(appContext, workerParameters, mapper, coinInfoDao, apiService)
    }
}