package com.example.forecastpro.pojo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Forecastday(
    @SerializedName("astro")
    @Expose
    val astro: Astro,
    @SerializedName("date")
    @Expose
    val date: String,
    @SerializedName("date_epoch")
    @Expose
    val dateEpoch: Int,
    @SerializedName("day")
    @Expose
    val day: Day,
    @SerializedName("hour")
    @Expose
    val hour: List<Hour>
)