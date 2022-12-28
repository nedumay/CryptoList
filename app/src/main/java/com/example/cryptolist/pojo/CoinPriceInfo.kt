package com.example.cryptolist.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinPriceInfo(
    @SerializedName("TYPE")
    @Expose
    val type: String? = null,
    @SerializedName("MARKET")
    @Expose
    val market: String? = null,
    @SerializedName("FROMSYMBOL")
    @Expose
    val fromsymbol: String? = null,
    @SerializedName("TOSYMBOL")
    @Expose
    val tosymbol: String? = null,
    @SerializedName("FLAGS")
    @Expose
    val flags: String? = null,
    @SerializedName("PRICE")
    @Expose
    val price: Double? = null,
    @SerializedName("LASTUPDATE")
    @Expose
    val lastupdate: Long? = null,
    @SerializedName("MEDIAN")
    @Expose
    val median: Double? = null,
    @SerializedName("LASTVOLUME")
    @Expose
    val lastvolume: Double? = null,
    @SerializedName("LASTVOLUMETO")
    @Expose
    val lastvolumeto: Double? = null,
    @SerializedName("LASTTRADEID")
    @Expose
    val lasttradeid: String? = null,
    @SerializedName("VOLUMEDAY")
    @Expose
    val volumeday: Double? = null,
    @SerializedName("VOLUMEDAYTO")
    @Expose
    val volumedayto: Double? = null,
    @SerializedName("VOLUME24HOUR")
    @Expose
    val volume24Hour: Double? = null,
    @SerializedName("VOLUME24HOURTO")
    @Expose
    val volume24Hourto: Double? = null,
    @SerializedName("OPENDAY")
    @Expose
    val openday: Double? = null,
    @SerializedName("HIGHDAY")
    @Expose
    val highday: Double? = null,
    @SerializedName("LOWDAY")
    @Expose
    val lowday: Double? = null,
    @SerializedName("OPEN24HOUR")
    @Expose
    val open24Hour: Double? = null,
    @SerializedName("HIGH24HOUR")
    @Expose
    val high24Hour: Double? = null,
    @SerializedName("LOW24HOUR")
    @Expose
    val low24Hour: Double? = null,
    @SerializedName("LASTMARKET")
    @Expose
    val lastmarket: String? = null,
    @SerializedName("VOLUMEHOUR")
    @Expose
    val volumehour: Double? = null,
    @SerializedName("VOLUMEHOURTO")
    @Expose
    val volumehourto: Double? = null,
    @SerializedName("OPENHOUR")
    @Expose
    val openhour: Double? = null,
    @SerializedName("HIGHHOUR")
    @Expose
    val highhour: Double? = null,
    @SerializedName("LOWHOUR")
    @Expose
    val lowhour: Double? = null,
    @SerializedName("TOPTIERVOLUME24HOUR")
    @Expose
    val toptiervolume24Hour: Double? = null,
    @SerializedName("TOPTIERVOLUME24HOURTO")
    @Expose
    val toptiervolume24Hourto: Double? = null,
    @SerializedName("CHANGE24HOUR")
    @Expose
    val change24Hour: Double? = null,
    @SerializedName("CHANGEPCT24HOUR")
    @Expose
    val changepct24Hour: Double? = null,
    @SerializedName("CHANGEDAY")
    @Expose
    val changeday: Double? = null,
    @SerializedName("CHANGEPCTDAY")
    @Expose
    val changepctday: Double? = null,
    @SerializedName("CHANGEHOUR")
    @Expose
    val changehour: Double? = null,
    @SerializedName("CHANGEPCTHOUR")
    @Expose
    val changepcthour: Double? = null,
    @SerializedName("CONVERSIONTYPE")
    @Expose
    val conversiontype: String? = null,
    @SerializedName("CONVERSIONSYMBOL")
    @Expose
    val conversionsymbol: String? = null,
    @SerializedName("SUPPLY")
    @Expose
    val supply: Double? = null,
    @SerializedName("MKTCAP")
    @Expose
    val mktcap: Double? = null,
    @SerializedName("MKTCAPPENALTY")
    @Expose
    val mktcappenalty: Long? = null,
    @SerializedName("CIRCULATINGSUPPLY")
    @Expose
    val circulatingsupply: Double? = null,
    @SerializedName("CIRCULATINGSUPPLYMKTCAP")
    @Expose
    val circulatingsupplymktcap: Double? = null,
    @SerializedName("TOTALVOLUME24H")
    @Expose
    val totalvolume24H: Double? = null,
    @SerializedName("TOTALVOLUME24HTO")
    @Expose
    val totalvolume24Hto: Double? = null,
    @SerializedName("TOTALTOPTIERVOLUME24H")
    @Expose
    val totaltoptiervolume24H: Double? = null,
    @SerializedName("TOTALTOPTIERVOLUME24HTO")
    @Expose
    val totaltoptiervolume24Hto: Double? = null,
    @SerializedName("IMAGEURL")
    @Expose
    val imageurl: String? = null
)
