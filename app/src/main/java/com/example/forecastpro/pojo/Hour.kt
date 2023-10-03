package com.example.forecastpro.pojo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.text.SimpleDateFormat
import java.util.Locale

data class Hour(
    @SerializedName("chance_of_rain")
    @Expose
    val chanceOfRain: Int,
    @SerializedName("chance_of_snow")
    @Expose
    val chanceOfSnow: Int,
    @SerializedName("cloud")
    @Expose
    val cloud: Int,
    @SerializedName("condition")
    @Expose
    val condition: Condition,
    @SerializedName("dewpoint_c")
    @Expose
    val dewpointC: Double,
    @SerializedName("dewpoint_f")
    @Expose
    val dewpointF: Double,
    @SerializedName("feelslike_c")
    @Expose
    val feelslikeC: Double,
    @SerializedName("feelslike_f")
    @Expose
    val feelslikeF: Double,
    @SerializedName("gust_kph")
    @Expose
    val gustKph: Double,
    @SerializedName("gust_mph")
    @Expose
    val gustMph: Double,
    @SerializedName("heatindex_c")
    @Expose
    val heatindexC: Double,
    @SerializedName("heatindex_f")
    @Expose
    val heatindexF: Double,
    @SerializedName("humidity")
    @Expose
    val humidity: Int,
    @SerializedName("is_day")
    @Expose
    val isDay: Int,
    @SerializedName("precip_in")
    @Expose
    val precipIn: Double,
    @SerializedName("precip_mm")
    @Expose
    val precipMm: Double,
    @SerializedName("pressure_in")
    @Expose
    val pressureIn: Double,
    @SerializedName("pressure_mb")
    @Expose
    val pressureMb: Double,
    @SerializedName("temp_c")
    @Expose
    val tempC: Double,
    @SerializedName("temp_f")
    @Expose
    val tempF: Double,
    @SerializedName("time")
    @Expose
    val time: String,
    @SerializedName("time_epoch")
    @Expose
    val timeEpoch: Int,
    @SerializedName("uv")
    @Expose
    val uv: Double,
    @SerializedName("vis_km")
    @Expose
    val visKm: Double,
    @SerializedName("vis_miles")
    @Expose
    val visMiles: Double,
    @SerializedName("will_it_rain")
    @Expose
    val willItRain: Int,
    @SerializedName("will_it_snow")
    @Expose
    val willItSnow: Int,
    @SerializedName("wind_degree")
    @Expose
    val windDegree: Int,
    @SerializedName("wind_dir")
    @Expose
    val windDir: String,
    @SerializedName("wind_kph")
    @Expose
    val windKph: Double,
    @SerializedName("wind_mph")
    @Expose
    val windMph: Double,
    @SerializedName("windchill_c")
    @Expose
    val windchillC: Double,
    @SerializedName("windchill_f")
    @Expose
    val windchillF: Double
) {
    fun getDateFromDateTime(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        val outputFormat = SimpleDateFormat("MMM, dd", Locale.US)
        val dateRes = inputFormat.parse(time)


        return dateRes?.let { outputFormat.format(it) }.toString()
    }
    fun getHours(): String {
        val parts = time.split(" ")
        return parts.getOrNull(1).toString()
    }
}