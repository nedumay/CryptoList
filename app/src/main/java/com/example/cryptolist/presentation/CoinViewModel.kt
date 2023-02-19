package com.example.cryptolist.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptolist.data.network.ApiFactory
import com.example.cryptolist.data.database.AppDatabase
import com.example.cryptolist.data.network.model.CoinInfoDto
import com.example.cryptolist.data.network.model.CoinInfoJsonContainerDto
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    fun getDetailInfo(fSym:String):LiveData<CoinInfoDto>{
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    init {
        loadData()
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map { it.names?.map { it.coinNameDto?.name }?.joinToString(",") }
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
        coinInfoJsonContainerDto: CoinInfoJsonContainerDto
    ): List<CoinInfoDto>{

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}