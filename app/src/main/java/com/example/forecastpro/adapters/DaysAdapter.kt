package com.example.forecastpro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastpro.OnItemClickListener
import com.example.forecastpro.R
import com.example.forecastpro.databinding.DayWeatherBinding
import com.example.forecastpro.pojo.Days
import com.example.forecastpro.pojo.Forecastday
import com.squareup.picasso.Picasso

class DaysAdapter() : ListAdapter<Forecastday, DaysAdapter.ViewHolder>(Comparator()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = DayWeatherBinding.bind(view)
        fun bind(day: Forecastday) = with(binding) {
            dayName.text = day.date
            dateDay.text = day.date
            temperature.text = "${day.day.maxtempC}°"
            Picasso.get().load("https:${day.day.condition.icon}").into(icon)
        }
    }

    class Comparator : DiffUtil.ItemCallback<Forecastday>() {
        override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.day_weather,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}