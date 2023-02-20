package com.example.cryptolist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptolist.R
import com.example.cryptolist.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var coinViewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        coinViewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        val rwCoinInfoList = findViewById<RecyclerView>(R.id.recyclerViewCoinPriceList)
        val coinAdapter = CoinInfoAdapter()
        rwCoinInfoList.adapter = coinAdapter
        coinViewModel.coinInfoList.observe(this){
            coinAdapter.submitList(it)
        }
        coinAdapter.onCoinClickListener = {
            startActivity(
                CoinDetailActivity.newIntent(this@CoinPriceListActivity, it.fromSymbol)
            )

        }
    }
}