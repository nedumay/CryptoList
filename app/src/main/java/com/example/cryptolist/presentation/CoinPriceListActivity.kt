package com.example.cryptolist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptolist.databinding.ActivityCoinPriceListBinding
import com.example.cryptolist.presentation.adapters.CoinInfoAdapter
import com.example.cryptolist.presentation.app.CoinApp
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var coinViewModel: CoinViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }
    private val component by lazy {
        (application as CoinApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        coinViewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

        val coinAdapter = CoinInfoAdapter()
        binding.recyclerViewCoinPriceList.adapter = coinAdapter
        coinViewModel.coinInfoList.observe(this) {
            coinAdapter.submitList(it)
        }

        coinAdapter.onCoinClickListener = {
            startActivity(
                CoinDetailActivity.newIntent(this@CoinPriceListActivity, it.fromSymbol)
            )
        }
    }
}