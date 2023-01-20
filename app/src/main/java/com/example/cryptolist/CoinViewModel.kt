package com.example.cryptolist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptolist.api.ApiFactory
import com.example.cryptolist.database.AppDatabase
import com.example.cryptolist.pojo.CoinPriceInfo
import com.example.cryptolist.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    fun getDetailInfo(fSym:String):LiveData<CoinPriceInfo>{
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    init {
        loadData()
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(15,TimeUnit.SECONDS) //устанвока интервала
            .repeat() //бесконечная загрузка (повтор) при успехе
            .retry() //бесконечная загрузка (повтор) если не успешно
            .subscribeOn(Schedulers.io())
            .subscribe({
                       db.coinPriceInfoDao().insertPriceList(it)
            },{
                Log.d("TEST", "Failure: ${it.message}")
            })
        compositeDisposable.add(disposable)
    }

    //Преобразование JsonObject в удобный формат для использования
    private fun getPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData): List<CoinPriceInfo>{
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for(coinKey in coinKeySet){
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for(currencyKey in currencyKeySet){
                val prcieInfo = Gson()
                    .fromJson(currencyJson.getAsJsonObject(currencyKey), CoinPriceInfo::class.java)
                result.add(prcieInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}