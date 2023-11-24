package com.kweather.forecastpro.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kweather.forecastpro.R
import com.kweather.forecastpro.databinding.HourWeatherBinding
import com.kweather.forecastpro.pojo.Hour
import com.squareup.picasso.Picasso

class HoursAdapter(private val context: Context) : ListAdapter<Hour, HoursAdapter.ViewHolder>(Comparator()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HourWeatherBinding.bind(view)
        fun bind(hour: Hour, context: Context) = with(binding) {
            val today = context.getString(R.string.today)
            val currentDate = hour.getCurrentDate()
            val hourTemp = hour.tempC.toInt()
            val tempSymbol = if (hourTemp >= 0) "+" else "-"
            val maxSign = if (hourTemp > 0) {
                "+"
            } else if (hourTemp < 0) {
                "-"
            } else {
                ""
            }
            hourName.text = hour.getHours()
            if (currentDate == hour.getDateFromDateTime()) {
                dateDay.text = today
            } else {
                dateDay.text = hour.getDateFromDateTime()
            }

            temperature.text =  when {
                hourTemp >= 0  -> String.format("%s%s°", maxSign, hourTemp)
                else -> String.format("%s°", hourTemp)
            }
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
        holder.bind(getItem(position), context)
    }
}