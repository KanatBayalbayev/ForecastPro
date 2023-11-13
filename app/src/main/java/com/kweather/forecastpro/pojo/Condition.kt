package com.kweather.forecastpro.pojo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Condition(
    @SerializedName("code")
    @Expose
    val code: Int,
    @SerializedName("icon")
    @Expose
    val icon: String,
    @SerializedName("text")
    @Expose
    val text: String
)