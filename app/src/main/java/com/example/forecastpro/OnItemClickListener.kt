package com.example.forecastpro

import com.example.forecastpro.pojo.CurrentDay
import com.example.forecastpro.pojo.Forecastday

interface OnItemClickListener {
    fun onItemClick(item: Forecastday)
}