package com.example.cryptolist.presentation.adapters

import androidx.recyclerview.widget.DiffUtil

import com.example.cryptolist.domain.CoinInfo

class CoinInfoDiffCallback:DiffUtil.ItemCallback<CoinInfo>() {
    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem == newItem
    }
}