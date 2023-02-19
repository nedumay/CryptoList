package com.example.cryptolist.domain

import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfo(
    val fromsymbol: String,
    val tosymbol: String?,
    val price: Double?,
    val lastupdate: Long?,
    val highday: Double?,
    val lowday: Double?,
    val lastmarket: String?,
    val imageurl: String?
)