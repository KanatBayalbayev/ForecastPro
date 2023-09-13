package com.example.forecastpro.pojo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Astro(
    @SerializedName("is_moon_up")
    @Expose
    val isMoonUp: Int,
    @SerializedName("is_sun_up")
    @Expose
    val isSunUp: Int,
    @SerializedName("moon_illumination")
    @Expose
    val moonIllumination: String,
    @SerializedName("moon_phase")
    @Expose
    val moonPhase: String,
    @SerializedName("moonrise")
    @Expose
    val moonrise: String,
    @SerializedName("moonset")
    @Expose
    val moonset: String,
    @SerializedName("sunrise")
    @Expose
    val sunrise: String,
    @SerializedName("sunset")
    @Expose
    val sunset: String
)