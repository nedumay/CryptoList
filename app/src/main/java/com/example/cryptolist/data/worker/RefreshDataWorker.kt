package com.example.cryptolist.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptolist.data.database.CoinInfoDao
import com.example.cryptolist.data.mapper.CoinMapper
import com.example.cryptolist.data.network.ApiService
import kotlinx.coroutines.delay

/** Class RefreshDataWorker for update data
 **/
class RefreshDataWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val mapper: CoinMapper,
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService
) : CoroutineWorker(context, workerParams) {



    override suspend fun doWork(): Result {
        while (true) {
            try {
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
                Log.d("Worker", "Success: ${dbModelList}")
            }catch (e: Exception){
                Log.d("Worker", "Error: ${e.message}")
            }
        }
        delay(10000)
    }

    companion object{
        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest{
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }
}