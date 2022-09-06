package com.example.cityairqualityapp.ui.detail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cityairqualityapp.data.entities.AirQuality
import com.example.cityairqualityapp.databinding.ReyclerviewItemLayoutBinding

class PreviousEntriesAdapter(
    private val previousEntriesList: List<AirQuality>
) :
    RecyclerView.Adapter<PreviousEntriesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ReyclerviewItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ReyclerviewItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        context = parent.context

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val airQuality = previousEntriesList[position]

        holder.binding.item1.text = airQuality.date
        holder.binding.item2.text = airQuality.time
        holder.binding.item3.text = airQuality.aqInfo.pollutant
        holder.binding.item4.text = airQuality.aqInfo.category

        holder.binding.listItemContainer.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", airQuality.id)
            intent.putExtra("city_name", airQuality.cityName)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return previousEntriesList.size
    }
}