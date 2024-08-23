package com.example.cryptolist.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptolist.data.database.CoinInfoDao
import com.example.cryptolist.data.mapper.CoinMapper
import com.example.cryptolist.data.network.ApiService
import com.example.cryptolist.domain.CoinInfo
import com.example.cryptolist.domain.CoinRepository
import com.example.cryptolist.worker.RefreshDataWorker
import kotlinx.coroutines.delay
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val mapper: CoinMapper,
    private val coinInfoDao: CoinInfoDao,
) : CoinRepository {

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override fun loadData() {
        val application = Application()
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }
}