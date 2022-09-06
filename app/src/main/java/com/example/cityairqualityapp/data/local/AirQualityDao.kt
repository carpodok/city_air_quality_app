package com.example.cityairqualityapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cityairqualityapp.data.entities.AirQuality

@Dao
interface AirQualityDao {

    @Query("SELECT * FROM airqualities")
    fun getAll(): List<AirQuality>

    @Query("SELECT * FROM airqualities WHERE cityName LIKE :cityName")
    fun getCityAllAirQuality(cityName: String): List<AirQuality>

    @Query("SELECT * FROM airqualities WHERE id = :id")
    fun getCurrentAirQualityWithID(id: Int): AirQuality

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(airQuality: AirQuality)

}