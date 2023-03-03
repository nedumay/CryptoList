package com.example.cryptolist.data.network

import com.example.cryptolist.pojo.CoinInfoListOfData
import com.example.cryptolist.pojo.CoinPriceInfoRawData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 30,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = USD,
    ): Single<CoinInfoListOfData>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = USD,
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
    ): Single<CoinPriceInfoRawData>

    companion object {
        private const val API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"

        private const val USD = "USD"
    }
}