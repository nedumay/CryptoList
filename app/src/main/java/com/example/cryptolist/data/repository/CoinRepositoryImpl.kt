package com.example.cryptolist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptolist.data.database.CoinInfoDao
import com.example.cryptolist.data.mapper.CoinMapper
import com.example.cryptolist.data.network.ApiService
import com.example.cryptolist.domain.CoinInfo
import com.example.cryptolist.domain.CoinRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val mapper: CoinMapper,
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService
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

    override suspend fun loadData() {
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
}