package com.example.cryptolist.presentation.app

import android.app.Application
import androidx.work.Configuration
import com.example.cryptolist.di.DaggerApplicationComponent
import com.example.cryptolist.data.worker.RefreshDataWorkerFactory
import javax.inject.Inject


class CoinApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: RefreshDataWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}