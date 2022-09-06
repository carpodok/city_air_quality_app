package com.example.cityairqualityapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cityairqualityapp.R
import com.example.cityairqualityapp.data.entities.AirQuality
import com.example.cityairqualityapp.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var adapter: PreviousEntriesAdapter

    private lateinit var airQuality: AirQuality

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailActivityRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]


        val ref = this
        lifecycleScope.launch(Dispatchers.IO) {

            val id = getID()

            // come from DetailActivity
            airQuality = if (id == 0) {
                getCurrAirQuality()


            }
            // come from previous entries list item
            else {
                detailViewModel.getCurrentAirQuality(id)

            }
            val list = getAllPreviousEntities() as ArrayList<AirQuality>

            // we do not want to currAirQuality to be in previous entries
            list.remove(airQuality)

            ref.runOnUiThread {
                setStationsView(airQuality)
                adapter = PreviousEntriesAdapter(list)
                binding.detailActivityRecyclerView.adapter = adapter
            }
        }
    }

    private fun setStationsView(airQuality: AirQuality) {

        binding.previousEntriesTW.append(" ${airQuality.cityName}")

        binding.currLocationTW.text = airQuality.cityName
        binding.timeTW.text = airQuality.time

        binding.station1TW.text = airQuality.co
        binding.station2TW.text = airQuality.no2
        binding.station3TW.text = airQuality.ozone
        binding.station4TW.text = airQuality.pm10
        binding.station5TW.text = airQuality.pm25
        binding.station6TW.text = airQuality.so2

    }

    private fun getCurrAirQuality(): AirQuality {
        return getAllPreviousEntities()[getAllPreviousEntities().size - 1]
    }

    private fun getAllPreviousEntities(): List<AirQuality> {
        return detailViewModel.getAllPastAirQualities(getCityName())
    }

    private fun getCityName(): String {

        // this value comes from both Search Activity and Detail Activity(itself)
        return intent.getStringExtra("city_name")!!
    }

    private fun getID(): Int {

        val intentValue = intent.getIntExtra("id", 0)

        // intent from Search Activity
        return if (intentValue == 0) {
            getCurrAirQuality().id
        }
        // intent from itself
        else {
            intentValue
        }
    }
}