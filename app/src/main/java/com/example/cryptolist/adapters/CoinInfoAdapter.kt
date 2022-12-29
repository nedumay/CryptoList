package com.example.cryptolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptolist.R
import com.example.cryptolist.pojo.CoinInfo
import com.example.cryptolist.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter: RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList : List<CoinPriceInfo> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onCoinClickListener: OnCoinClickListener? = null

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewLogoCoin = itemView.findViewById<ImageView>(R.id.imageViewLogo)
        val textViewSymbol = itemView.findViewById<TextView>(R.id.textViewSymbol)
        val textViewPrice = itemView.findViewById<TextView>(R.id.textViewPrice)
        val textViewTime = itemView.findViewById<TextView>(R.id.textViewLastUpdate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        holder.textViewSymbol.text = coin.fromsymbol
        holder.textViewPrice.text = coin.price.toString() + "$"
        holder.textViewTime.text = "Last update: ${coin.getFormattedTime()}"
        Picasso.get().load(coin.getFullImageUrl()).into(holder.imageViewLogoCoin)
        holder.itemView.setOnClickListener {
            onCoinClickListener?.onCoinClick(coin)
        }

    }

    override fun getItemCount() = coinInfoList.size

    interface OnCoinClickListener{
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}