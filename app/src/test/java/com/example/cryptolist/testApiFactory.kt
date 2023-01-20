package com.example.cryptolist

import com.example.cryptolist.api.ApiFactory
import com.example.cryptolist.api.ApiService
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class testApiFactory {
    @Test
    fun testApiFactory(){
        val instance = ApiFactoryTest.retrofit
        assert(instance.baseUrl().url().toString() == ApiFactoryTest.BASE_URL_TEST)
    }

}

object ApiFactoryTest{

    const val BASE_URL_TEST = "https://min-api.cryptocompare.com/data/"

    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL_TEST)
        .build()

}