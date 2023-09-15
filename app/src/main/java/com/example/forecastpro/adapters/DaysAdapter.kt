package com.example.forecastpro.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastpro.R
import com.example.forecastpro.databinding.DayWeatherBinding
import com.example.forecastpro.fragments.DaysFragment
import com.example.forecastpro.pojo.Forecastday
import com.squareup.picasso.Picasso

class DaysAdapter(
    private val listener: DaysFragment,
    private val context: Context,

    ) : ListAdapter<Forecastday, DaysAdapter.ViewHolder>(Comparator()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = DayWeatherBinding.bind(view)
        fun bind(day: Forecastday, context: Context) = with(binding) {
            val maxMinTempString = context.getString(R.string.maxMinTemp)
            dayName.text = day.getDayOfWeek()
            dateDay.text = day.formatMonthAndDay()
            if (day.day.maxtempC.toInt() > 0 && day.day.mintempC.toInt() > 0) {
                temperature.text = String.format(
                    "+%s°/+%s°",
                    day.day.maxtempC.toInt(),
                    day.day.mintempC.toInt()
                )
            } else if (day.day.maxtempC.toInt() > 0 && day.day.mintempC.toInt() < 0) {
                temperature.text = String.format(
                    "+%s°/-%s°",
                    day.day.maxtempC.toInt(),
                    day.day.mintempC.toInt()
                )
            } else if (day.day.maxtempC.toInt() < 0 && day.day.mintempC.toInt() > 0) {
                temperature.text = String.format(
                    "-%s°/+%s°",
                    day.day.maxtempC.toInt(),
                    day.day.mintempC.toInt()
                )
            } else if (day.day.maxtempC.toInt() < 0 && day.day.mintempC.toInt() < 0) {
                temperature.text = String.format(
                    "-%s°/-%s°",
                    day.day.maxtempC.toInt(),
                    day.day.mintempC.toInt()
                )
            }

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
        holder.bind(item, context)
        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
    }
}