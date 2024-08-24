package com.example.cryptolist.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptolist.databinding.ActivityCoinDetailBinding
import com.example.cryptolist.domain.CoinInfo
import com.example.cryptolist.presentation.app.CoinApp
import com.klim.tcharts.entities.ChartData
import com.klim.tcharts.entities.ChartItem
import com.squareup.picasso.Picasso
import javax.inject.Inject
import kotlin.random.Random


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

                //setupGraph(it, binding)

            }
        })
    }

    private fun setupGraph(data: CoinInfo, binding: ActivityCoinDetailBinding) {
        val keys = ArrayList<String>() //keys for each chart
        val names = ArrayList<String>() //names for chart
        val colors = ArrayList<Int>() //colors for lines
        val items = ArrayList<ChartItem>() //charts value for some time

        keys.add(data.fromSymbol)
        names.add("Green Line")
        colors.add(Color.GREEN)

        // Начало времени
        var startTime = 1614542230000L
        startTime+=data.lastUpdate!!.toLong()*1000

        val random: Random = Random(startTime)
        val values = ArrayList<Int>()
        for (j in keys.indices) {
            values.add(random.nextInt(1000))
        }

        val chartItem = ChartItem(startTime, values)
        items.add(chartItem)

        val chartData: ChartData = ChartData(keys, names, colors, items)
        binding.graphView.setData(chartData, true)
        binding.graphView.setTitle(String.format("Chart #%d", 1))
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