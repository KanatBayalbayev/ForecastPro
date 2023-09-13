package com.example.forecastpro.pojo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Forecast(
    @SerializedName("forecastday")
    @Expose
    val forecastday: List<Forecastday>
)