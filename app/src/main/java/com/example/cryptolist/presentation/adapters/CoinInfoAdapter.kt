package com.example.cryptolist.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cryptolist.R
import com.example.cryptolist.data.network.ApiFactory.BASE_IMAGE_URL
import com.example.cryptolist.domain.CoinInfo
import com.example.cryptolist.utils.convertTimesToTime
import com.squareup.picasso.Picasso

class CoinInfoAdapter: ListAdapter<CoinInfo, CoinInfoViewHolder>(CoinInfoDiffCallback()) {

    var onCoinClickListener : ((CoinInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        holder.textViewSymbol.text = coin.fromSymbol
        holder.textViewPrice.text = "${coin.price.toString()}$"
        holder.textViewTime.text = "Last update: ${convertTimesToTime(coin.lastUpdate)}"
        Picasso.get().load(BASE_IMAGE_URL + coin.imageUrl).into(holder.imageViewLogoCoin)
        holder.itemView.setOnClickListener {
            onCoinClickListener?.invoke(coin)
        }

    }

}