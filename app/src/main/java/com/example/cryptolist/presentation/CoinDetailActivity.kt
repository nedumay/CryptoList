package com.example.cryptolist.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptolist.R
import com.example.cryptolist.databinding.ActivityCoinDetailBinding
import com.example.cryptolist.domain.CoinInfo
import com.example.cryptolist.presentation.app.CoinApp
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

    @SuppressLint("DefaultLocale", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }

        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)!!
        coinViewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]

        val series = LineGraphSeries(arrayOf())
        series.color = ContextCompat.getColor(this, R.color.graphLineColor)

        binding.graphView.addSeries(series)

        binding.graphView.viewport.apply {
            isScrollable = true
            setScalable(true)
            setScrollableY(true)
            isScalable = true
            setScalableY(true)
        }

        binding.graphView.gridLabelRenderer.apply {
            horizontalAxisTitle = "Time"
            verticalAxisTitle = "Price $"
            isHorizontalLabelsVisible = true
            isVerticalLabelsVisible = true

        }


        coinViewModel.getDetailInfo(fromSymbol).observe(this, Observer {
            with(binding) {
                val newPrice = formattedPrice(it.price ?: "0.0")
                textViewPrice.text = "$newPrice $"
                val newLowDay = formattedPrice(it.lowDay ?: "0.0")
                textViewMinPrice.text = "$newLowDay $"
                val newHighDay = formattedPrice(it.highDay ?: "0.0")
                textViewMaxPrice.text = "$newHighDay $"
                textViewLastDeal.text = it.lastMarket
                textViewUpdate.text = it.lastUpdate
                textViewSymbol.text = it.fromSymbol
                Picasso.get().load(it.imageUrl).into(imageViewLogo)

                val price = it.price?.toDoubleOrNull() ?: 0.0
                val lastUpdate = Date(System.currentTimeMillis())

                series.appendData(DataPoint(lastUpdate, price), true, 10000000)
                binding.graphView.onDataChanged(true, true)

                binding.graphView.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
                    override fun formatLabel(value: Double, isValueX: Boolean): String {
                        return if (isValueX) {
                            val date = Date(value.toLong())
                            SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(date)
                        } else {
                            super.formatLabel(value, isValueX)
                        }
                    }
                }
            }
        })
    }

    @SuppressLint("DefaultLocale")
    private fun formattedPrice(price: String): String {
        val newPrice = price.toDouble()
        val formattedPrice = String.format("%.2f", newPrice)
        return formattedPrice
    }


    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}