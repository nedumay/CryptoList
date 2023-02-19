package com.example.cryptolist.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptolist.data.database.AppDatabase
import com.example.cryptolist.data.mapper.CoinMapper
import com.example.cryptolist.data.network.ApiFactory
import com.example.cryptolist.domain.CoinInfo
import com.example.cryptolist.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(private val application: Application): CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()
    private val apiService = ApiFactory.apiService

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()){
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromsymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromsymbol)){
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true){
            //получаем топ 50 популярных валют
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            // преобразуем все валюты в одну строку
            val fSymbols = mapper.mapCoinListToString(topCoins)
            // по строке получаем все данные из сети
            val jsonContainer = apiService.getFullPriceList(fSyms = fSymbols)
            // из контейнера преобразуем в Dto коллекцию
            val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
            // из Dto коллекции в БД коллекцию
            val dbModelList = coinInfoDtoList.map{ mapper.mapDtoToDbModel(it)}
            // Вставляем данные в базу
            coinInfoDao.insertPriceList(dbModelList)
            delay(10000)
        }

    }
}