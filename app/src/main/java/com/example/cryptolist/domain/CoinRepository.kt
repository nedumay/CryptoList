package com.example.cryptolist.domain

import androidx.lifecycle.LiveData

interface CoinRepository {

    fun getCoinInfoList():LiveData<List<CoinInfo>>

    fun getCoinInfo(fromsymbol:String): LiveData<CoinInfo>

    suspend fun loadData()

}