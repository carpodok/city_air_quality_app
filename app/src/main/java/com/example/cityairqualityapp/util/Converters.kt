package com.example.cityairqualityapp.util

import androidx.room.TypeConverter
import com.example.cityairqualityapp.data.entities.AQInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    // for aqiInfo in stations in API
    @TypeConverter
    fun fromAQInfo(info: AQInfo?): String? {
        if (info == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<AQInfo?>() {}.type
        return gson.toJson(info, type)
    }

    @TypeConverter
    fun toAQInfo(infoString: String?): AQInfo? {
        if (infoString == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<AQInfo?>() {}.type
        return gson.fromJson<AQInfo>(
            infoString,
            type
        )
    }


}