package com.example.forecastpro.adapters

data class DayModel(
    val cityName: String,
    val date: String,
    val day: String,
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val icon: String,
    val hours: String,
)
