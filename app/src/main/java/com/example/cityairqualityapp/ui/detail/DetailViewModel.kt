package com.example.cityairqualityapp.ui.detail

import androidx.lifecycle.ViewModel
import com.example.cityairqualityapp.data.entities.AirQuality
import com.example.cityairqualityapp.data.repository.AirQualityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: AirQualityRepository
) : ViewModel() {

    fun getAllPastAirQualities(cityName: String): List<AirQuality> {
        return repository.getAllPastData(cityName)
    }

    fun getCurrentAirQuality(id: Int): AirQuality {
        return repository.getCurrentAirQuality(id)
    }

}