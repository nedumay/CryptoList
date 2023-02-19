package com.example.cryptolist.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptolist.data.network.model.CoinInfoDto

class CoinInfoDiffCallback:DiffUtil.ItemCallback<CoinInfoDto>() {
    override fun areItemsTheSame(oldItem: CoinInfoDto, newItem: CoinInfoDto): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: CoinInfoDto, newItem: CoinInfoDto): Boolean {
        return oldItem == newItem
    }
}