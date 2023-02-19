package com.example.cryptolist.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cryptolist.R
import com.example.cryptolist.data.network.model.CoinInfoDto
import com.squareup.picasso.Picasso

class CoinInfoAdapter: ListAdapter<CoinInfoDto, CoinInfoViewHolder>(CoinInfoDiffCallback()) {

    var onCoinClickListener : ((CoinInfoDto) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        holder.textViewSymbol.text = coin.fromsymbol
        holder.textViewPrice.text = "${coin.price.toString()}$"
        holder.textViewTime.text = "Last update: ${coin.getFormattedTime()}"
        Picasso.get().load(coin.getFullImageUrl()).into(holder.imageViewLogoCoin)
        holder.itemView.setOnClickListener {
            onCoinClickListener?.invoke(coin)
        }

    }

}