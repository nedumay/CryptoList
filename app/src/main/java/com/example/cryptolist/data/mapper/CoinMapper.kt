package com.example.cryptolist.data.mapper

import com.example.cryptolist.data.database.CoinInfoDbModel
import com.example.cryptolist.data.network.model.CoinInfoDto
import com.example.cryptolist.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptolist.data.network.model.CoinNamesListDto
import com.example.cryptolist.domain.CoinInfo
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CoinMapper @Inject constructor() {
    // mapper для преобразование из Dto в модель базы данных
    fun mapDtoToDbModel(dto: CoinInfoDto): CoinInfoDbModel {

        return CoinInfoDbModel(
            fromSymbol = dto.fromsymbol,
            toSymbol = dto.tosymbol,
            price = dto.price,
            lastUpdate = dto.lastupdate,
            highDay = dto.highday,
            lowDay = dto.lowday,
            lastMarket = dto.lastmarket,
            imageUrl = BASE_IMAGE_URL + dto.imageurl
        )
    }

    // mapper для перевода из JsonObject в mutableListOf<CoinInfoDto>
    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.jsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    // для преобразования List в одну строку через запятую
    fun mapCoinListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map {
            it.coinNameDto?.name
        }?.joinToString(",") ?: ""
    }

    // переобразование DbModel в Entity
    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo {
        return CoinInfo(
            fromSymbol = dbModel.fromSymbol,
            toSymbol = dbModel.toSymbol,
            price = dbModel.price,
            lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
            highDay = dbModel.highDay,
            lowDay = dbModel.lowDay,
            lastMarket = dbModel.lastMarket,
            imageUrl = dbModel.imageUrl
        )
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {

        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}