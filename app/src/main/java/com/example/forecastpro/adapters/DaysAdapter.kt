package com.example.forecastpro.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastpro.OnItemClickListener
import com.example.forecastpro.R
import com.example.forecastpro.databinding.DayWeatherBinding
import com.example.forecastpro.pojo.Forecastday
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class DaysAdapter(
    private val listener: OnItemClickListener,
    private val context: Context,

    ) : ListAdapter<Forecastday, DaysAdapter.ViewHolder>(Comparator()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = DayWeatherBinding.bind(view)
        fun bind(day: Forecastday, context: Context) = with(binding) {
            val today = context.getString(R.string.today)
            val formattedDate = day.getFormattedDate()
            if (formattedDate == day.formatMonthAndDay()) {
                dateDay.text = today
            } else {
                dateDay.text = day.formatMonthAndDay()
            }
            dayName.text = day.getDayOfWeek()
//            dateDay.text = day.formatMonthAndDay()
            val maxTemp = day.day.maxtempC.toInt()
            val minTemp = day.day.mintempC.toInt()

            val maxTempSymbol = if (maxTemp > 0) "+" else "-"
            val minTempSymbol = if (minTemp > 0) "+" else "-"

            temperature.text = when {
                maxTemp >= 0 && minTemp >= 0 -> String.format("%s%s°/%s%s°", maxTempSymbol, maxTemp, minTempSymbol, minTemp)
                else -> String.format("%s%s°/%s%s°", maxTemp, minTemp)
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