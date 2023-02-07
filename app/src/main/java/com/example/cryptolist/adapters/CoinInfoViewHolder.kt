package com.example.cryptolist.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptolist.R

class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val imageViewLogoCoin = itemView.findViewById<ImageView>(R.id.imageViewLogo)
    val textViewSymbol = itemView.findViewById<TextView>(R.id.textViewSymbol)
    val textViewPrice = itemView.findViewById<TextView>(R.id.textViewPrice)
    val textViewTime = itemView.findViewById<TextView>(R.id.textViewLastUpdate)
}