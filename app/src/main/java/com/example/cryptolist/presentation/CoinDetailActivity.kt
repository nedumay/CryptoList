package com.example.cryptolist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptolist.R
import com.example.cryptolist.data.network.ApiFactory.BASE_IMAGE_URL
import com.example.cryptolist.utils.convertTimesToTime
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var coinViewModel: CoinViewModel
    private lateinit var textViewPrice:TextView
    private lateinit var textViewMinPrice:TextView
    private lateinit var textViewMaxPrice:TextView
    private lateinit var textViewLastDeal:TextView
    private lateinit var textViewTime :TextView
    private lateinit var textViewSymbol :TextView
    private lateinit var imageViewLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)

        if(!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        initView()
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)!!
        coinViewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        coinViewModel.getDetailInfo(fromSymbol).observe(this){
            textViewPrice.text = it.price.toString() + "$"
            textViewMinPrice.text = it.lowDay.toString() + "$"
            textViewMaxPrice.text = it.highDay.toString() + "$"
            textViewLastDeal.text = it.lastMarket
            textViewTime.text = convertTimesToTime(it.lastUpdate)
            textViewSymbol.text = it.fromSymbol
            Picasso.get().load(BASE_IMAGE_URL + it.imageUrl).into(imageViewLogo)
        }
    }

    private fun initView() {
        textViewPrice = findViewById(R.id.textViewPrice)
        textViewMinPrice = findViewById(R.id.textViewMinPrice)
        textViewMaxPrice = findViewById(R.id.textViewMaxPrice)
        textViewLastDeal = findViewById(R.id.textViewLastDeal)
        textViewTime = findViewById(R.id.textViewUpdate)
        imageViewLogo = findViewById(R.id.imageViewLogo)
        textViewSymbol = findViewById(R.id.textViewSymbol)
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