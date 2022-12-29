package com.example.cryptolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptolist.adapters.CoinInfoAdapter
import com.example.cryptolist.pojo.CoinPriceInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var coinViewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        coinViewModel = ViewModelProviders.of(this)[CoinViewModel::class.java]

        val rwCoinInfoLsit = findViewById<RecyclerView>(R.id.recyclerViewCoinPriceList)
        val coinAdapter = CoinInfoAdapter()
        rwCoinInfoLsit.adapter = coinAdapter
        coinViewModel.priceList.observe(this, Observer {
            coinAdapter.coinInfoList = it
        })
        coinAdapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                startActivity(
                    CoinDetailActivity
                        .newIntent(this@CoinPriceListActivity, coinPriceInfo.fromsymbol)
                )
            }
        }
    }
}