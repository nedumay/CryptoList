package com.example.cryptolist.presentation.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cryptolist.databinding.ItemCoinInfoBinding
import com.example.cryptolist.domain.CoinInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter: ListAdapter<CoinInfo, CoinInfoViewHolder>(CoinInfoDiffCallback) {

    var onCoinClickListener: ((CoinInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        with(holder.binding) {
            with(coin) {
                textViewSymbol.text = fromSymbol
                textViewPrice.text = price + "$"
                textViewLastUpdate.text = "Last update: $lastUpdate"
                Picasso.get().load(imageUrl).into(imageViewLogo)
                root.setOnClickListener {
                    onCoinClickListener?.invoke(this)
                }
            }
        }


    }

}