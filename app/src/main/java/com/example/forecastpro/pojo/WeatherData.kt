package com.example.forecastpro.pojo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class WeatherData(
    @SerializedName("current")
    @Expose
    val current: Current,
    @SerializedName("forecast")
    @Expose
    val forecast: Forecast,
    @SerializedName("location")
    @Expose
    val location: Location
)