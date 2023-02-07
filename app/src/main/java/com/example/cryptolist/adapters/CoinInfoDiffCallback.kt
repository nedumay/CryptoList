package com.example.cryptolist.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptolist.pojo.CoinPriceInfo

class CoinInfoDiffCallback:DiffUtil.ItemCallback<CoinPriceInfo>() {
    override fun areItemsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo): Boolean {
        return oldItem == newItem
    }
}