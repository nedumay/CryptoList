package com.example.cryptolist.di

import android.app.Application
import com.example.cryptolist.presentation.CoinDetailActivity
import com.example.cryptolist.presentation.CoinPriceListActivity
import com.example.cryptolist.presentation.app.CoinApp
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [DataModule::class,ViewModelModule::class]
)
interface ApplicationComponent {

    fun inject(activity: CoinPriceListActivity)

    fun inject(activity: CoinDetailActivity)

    fun inject(application: CoinApp)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ):ApplicationComponent
    }
}