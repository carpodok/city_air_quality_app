package com.example.cityairqualityapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "airqualities")
data class AirQuality(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    var time: String,

    var date: String,

    var cityName: String,

    @SerializedName("CO")
    val co: String,

    @SerializedName("NO2")
    val no2: String,

    @SerializedName("OZONE")
    val ozone: String,

    @SerializedName("PM10")
    val pm10: String,

    @SerializedName("PM25")
    val pm25: String,

    @SerializedName("SO2")
    val so2: String,

    @SerializedName("aqiInfo")
    val aqInfo: AQInfo

)