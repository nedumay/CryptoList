package com.example.cryptolist.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptolist.data.database.CoinInfoDao
import com.example.cryptolist.data.mapper.CoinMapper
import com.example.cryptolist.data.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

/** Class RefreshDataWorker for update data
 **/
class RefreshDataWorker(
    context: Context,
    private val workerParams: WorkerParameters,
    private val mapper: CoinMapper,
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService
) : CoroutineWorker(context, workerParams) {



    override suspend fun doWork(): Result {
        while (true) {
            //получаем топ 50 популярных валют
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            // преобразуем все валюты в одну строку
            val fSymbols = mapper.mapCoinListToString(topCoins)
            // по строке получаем все данные из сети
            val jsonContainer = apiService.getFullPriceList(fSyms = fSymbols)
            // из контейнера преобразуем в Dto коллекцию
            val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
            // из Dto коллекции в БД коллекцию
            val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
            // Вставляем данные в базу
            coinInfoDao.insertPriceList(dbModelList)
            delay(10000)
        }
    }

    companion object{
        const val NAME = "RefreshDataWorker"

        fun makeRequest() = OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
    }
}