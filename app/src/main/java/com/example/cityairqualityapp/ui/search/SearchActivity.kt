package com.example.cityairqualityapp.ui.search

import android.app.SearchManager
import android.content.Intent
import android.database.Cursor
import android.database.MatrixCursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cityairqualityapp.R
import com.example.cityairqualityapp.databinding.ActivitySearchBinding
import com.example.cityairqualityapp.ui.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchViewModel: SearchViewModel

    val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
    val to = intArrayOf(R.id.searchItemID)

    var suggestions = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]


        // getting previous cities for suggestion of searchview
        lifecycleScope.launch(Dispatchers.IO) {
            suggestions = getSavedCities()
        }

        val cursorAdapter = SimpleCursorAdapter(
            this,
            R.layout.suggestion_item_layout,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )

        binding.citySearchView.suggestionsAdapter = cursorAdapter

        binding.citySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                val cursor =
                    MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                newText?.let {

                    suggestions.forEachIndexed { index, suggestion ->
                        if (suggestion.contains(newText, true))
                            cursor.addRow(arrayOf(index, suggestion))
                    }
                }

                cursorAdapter.changeCursor(cursor)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

        binding.citySearchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = binding.citySearchView.suggestionsAdapter.getItem(position) as Cursor
                val selection =
                    cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                binding.citySearchView.setQuery(selection, false)

                search(selection)

                return true
            }

            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }
        })

        binding.searchButton.setOnClickListener {
            val cityNameInput = binding.citySearchView.query
            if (cityNameInput.isEmpty()) {

                giveEmptyEditTextWarning()

            } else {
                search(cityNameInput.toString())
            }
        }
    }

    private fun search(cityName: String) {
        searchViewModel.loadDataa(cityName)
        searchViewModel.getLiveData().observe(this) {
            if (it != null) {

                binding.progressBar.visibility = View.INVISIBLE
                val currAirQuality = it.stations[0]

                currAirQuality.time = getUpToDateTimeAndDate()[0]
                currAirQuality.date = getUpToDateTimeAndDate()[1]

                currAirQuality.cityName = cityName.uppercase()
                searchViewModel.insertData(currAirQuality)

                intentToDetail(currAirQuality.cityName)
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                binding.errorTW.visibility = View.VISIBLE
                giveNotFoundWarning()
            }
        }

        searchViewModel.loading.observe(this) {
            if (it == true) {
                binding.progressBar.visibility = View.VISIBLE
                binding.errorTW.visibility = View.INVISIBLE

            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun giveNotFoundWarning() {
        Snackbar.make(
            binding.searchButton,
            "No air quality found for the searched city",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun giveEmptyEditTextWarning() {
        val snackbar = Snackbar.make(
            binding.searchButton,
            "You need to type a city name",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar
            .setAction("OK") {
                snackbar.dismiss()
            }
            .show()
    }

    // list[0] -> formatted time
    // list[1] -> formatted date
    private fun getUpToDateTimeAndDate(): ArrayList<String> {
        val list = ArrayList<String>()

        val current = LocalDateTime.now()
        val timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT)
        val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

        val time = current.format(timeFormatter)
        val date = current.format(dateFormatter)

        list.add(time)
        list.add(date)

        return list
    }

    private fun intentToDetail(cityName: String) {
        val intent = Intent(this@SearchActivity, DetailActivity::class.java)
        intent.putExtra("city_name", cityName)
        startActivity(intent)
    }

    private fun getSavedCities(): ArrayList<String> {

        val cities = ArrayList<String>()

        for (i in searchViewModel.getAll()) {
            if (!cities.contains(i.cityName)) {
                cities.add(i.cityName)
            }
        }

        return cities
    }


    companion object {

        private const val TIME_FORMAT = "h:mm a"
        private const val DATE_FORMAT = "dd-MMM-yyyy"
    }
}