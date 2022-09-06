package com.example.cityairqualityapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityairqualityapp.data.entities.AirQuality
import com.example.cityairqualityapp.data.entities.AirQuantityList
import com.example.cityairqualityapp.data.repository.AirQualityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: AirQualityRepository,
) : ViewModel() {

    private val airQuality = MutableLiveData<AirQuantityList>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun getLiveData(): MutableLiveData<AirQuantityList> {
        return airQuality
    }

    fun loadDataa(cityName: String) {
        loading.value = true
        repository.makeApiCall(cityName, airQuality)
    }

    fun getAll(): List<AirQuality> {
        return repository.getAll()
    }

    fun insertData(airQuality: AirQuality) {

        viewModelScope.launch {
            repository.insertCurrentAirQuality(airQuality)
        }
    }
}