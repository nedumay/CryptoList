package com.example.cryptolist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptolist.databinding.ActivityCoinDetailBinding
import com.example.cryptolist.presentation.app.CoinApp
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var coinViewModel: CoinViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }
    private val component by lazy {
        (application as CoinApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }

        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)!!
        coinViewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]

        coinViewModel.getDetailInfo(fromSymbol).observe(this, Observer {
            with(binding){
                textViewPrice.text = it.price + "$"
                textViewMinPrice.text = it.lowDay + "$"
                textViewMaxPrice.text = it.highDay + "$"
                textViewLastDeal.text = it.lastMarket
                textViewUpdate.text = it.lastUpdate
                textViewSymbol.text = it.fromSymbol
                Picasso.get().load(it.imageUrl).into(imageViewLogo)
            }
        })
    }

    companion object{
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol:String):Intent{
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL,fromSymbol)
            return intent
        }
    }
}