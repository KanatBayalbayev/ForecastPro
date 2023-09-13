package com.example.forecastpro.pojo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Location(
    @SerializedName("country")
    @Expose
    val country: String,
    @SerializedName("lat")
    @Expose
    val lat: Double,
    @SerializedName("localtime")
    @Expose
    val localtime: String,
    @SerializedName("localtime_epoch")
    @Expose
    val localtimeEpoch: Int,
    @SerializedName("lon")
    @Expose
    val lon: Double,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("region")
    @Expose
    val region: String,
    @SerializedName("tz_id")
    @Expose
    val tzId: String
)