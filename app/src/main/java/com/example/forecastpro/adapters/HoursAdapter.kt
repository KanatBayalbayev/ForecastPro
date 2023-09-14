package com.example.forecastpro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastpro.R
import com.example.forecastpro.databinding.DayWeatherBinding
import com.example.forecastpro.databinding.HourWeatherBinding
import com.example.forecastpro.pojo.Forecastday
import com.example.forecastpro.pojo.Hour
import com.squareup.picasso.Picasso

class HoursAdapter : ListAdapter<Hour, HoursAdapter.ViewHolder>(Comparator()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HourWeatherBinding.bind(view)
        fun bind(hour: Hour) = with(binding) {
            hourName.text = hour.getTimeFromDateTime()
            dateDay.text = hour.formatDate()
            temperature.text  = "${hour.tempC.toInt()}°"
            Picasso.get().load("https:${hour.condition.icon}").into(icon)

        }
    }

    class Comparator : DiffUtil.ItemCallback<Hour>() {
        override fun areItemsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.hour_weather,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}