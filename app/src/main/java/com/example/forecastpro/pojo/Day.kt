package com.example.forecastpro.pojo

data class Day(
    val cityName: String,
    val date: String,
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val icon: String,
    val hours: String,
)
