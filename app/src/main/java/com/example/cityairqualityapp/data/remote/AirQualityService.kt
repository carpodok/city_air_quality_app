package com.example.cityairqualityapp.data.remote

import com.example.cityairqualityapp.data.entities.AirQuantityList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityService {

    @GET("latest/by-city")
    fun getAirQuality(@Query("city") cityName: String): Call<AirQuantityList>

}