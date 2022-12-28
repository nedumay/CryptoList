package com.example.cryptolist.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class CoinPriceInfoRawData(
    @SerializedName("RAW")
    @Expose
    val coinPriceInfoJsonObject: JSONObject? = null
)
