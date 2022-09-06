package com.example.cityairqualityapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cityairqualityapp.data.entities.AirQuality
import com.example.cityairqualityapp.data.entities.AirQuantityList
import com.example.cityairqualityapp.data.local.AirQualityDao
import com.example.cityairqualityapp.data.remote.AirQualityService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AirQualityRepository @Inject constructor(

    private val airQualityDao: AirQualityDao,
    private val service: AirQualityService,

    ) {

    fun makeApiCall(cityName: String, liveData: MutableLiveData<AirQuantityList>) {
        val call: Call<AirQuantityList> = service.getAirQuality(cityName)
        call.enqueue(object : Callback<AirQuantityList> {
            override fun onFailure(call: Call<AirQuantityList>, t: Throwable) {
                liveData.postValue(null)
            }

            override fun onResponse(
                call: Call<AirQuantityList>,
                response: Response<AirQuantityList>
            ) {

                liveData.postValue(response.body())
            }
        })
    }

    fun getAllPastData(cityName: String): List<AirQuality> {

        return airQualityDao.getCityAllAirQuality(cityName)
    }

    fun getAll(): List<AirQuality> {
        return airQualityDao.getAll()
    }

    fun getCurrentAirQuality(id: Int): AirQuality {

        val airQuality = airQualityDao.getCurrentAirQualityWithID(id)
        Log.d(TAG, "getCurrentAirQuality: ${airQuality.id}")

        return airQuality
    }

    suspend fun insertCurrentAirQuality(airQuality: AirQuality) {

        airQualityDao.insert(airQuality)

    }

    companion object {
        private const val TAG = "AirQualityRepository"
    }

}