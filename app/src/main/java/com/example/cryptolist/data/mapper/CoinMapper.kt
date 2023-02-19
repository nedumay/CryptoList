package com.example.cryptolist.data.mapper

import com.example.cryptolist.data.database.CoinInfoDbModel
import com.example.cryptolist.data.network.model.CoinInfoDto
import com.example.cryptolist.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptolist.data.network.model.CoinNamesListDto
import com.example.cryptolist.domain.CoinInfo
import com.google.gson.Gson

class CoinMapper {
    // mapper для преобразование из Dto в модель базы данных
    fun mapDtoToDbModel(dto: CoinInfoDto):CoinInfoDbModel{

        return CoinInfoDbModel(
            fromsymbol = dto.fromsymbol,
            tosymbol = dto.tosymbol,
            price = dto.price,
            lastupdate = dto.lastupdate,
            highday = dto.highday,
            lowday = dto.lowday,
            lastmarket = dto.lastmarket,
            imageurl = dto.imageurl
        )
    }
    // mapper для перевода из JsonObject в mutableListOf<CoinInfoDto>
    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto):List<CoinInfoDto>{
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.jsonObject ?: return result
        val coinKeySet = jsonObject.keySet() // получаем набор ключей у jsonObject
        for(coinKey in coinKeySet){
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for(currencyKey in currencyKeySet){
                val priсeInfo = Gson()
                    .fromJson(
                        currencyJson.getAsJsonObject(currencyKey),
                        CoinInfoDto::class.java
                    )
                result.add(priсeInfo)
            }
        }
        return result
    }
    // для преобразования List в одну строку через запятую
    fun mapCoinListToString(namesListDto: CoinNamesListDto):String{
        return namesListDto.names?.map {
            it.coinNameDto?.name
        }?.joinToString(",") ?: ""
    }
    // переобразование DbModel в Entity
    fun mapDbModelToEntity(dbModel: CoinInfoDbModel):CoinInfo{
        return CoinInfo(
            fromsymbol = dbModel.fromsymbol,
            tosymbol = dbModel.tosymbol,
            price = dbModel.price,
            lastupdate = dbModel.lastupdate,
            highday = dbModel.highday,
            lowday = dbModel.lowday,
            lastmarket = dbModel.lastmarket,
            imageurl = dbModel.imageurl
        )
    }
}