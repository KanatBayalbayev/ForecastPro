package com.example.forecastpro.pojo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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
) {
    fun getDayOfWeek(): String {
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)

        val localDate = LocalDate.parse(date, inputFormat)

        return when (localDate.dayOfWeek) {
            DayOfWeek.MONDAY -> "Monday"
            DayOfWeek.TUESDAY -> "Tuesday"
            DayOfWeek.WEDNESDAY -> "Wednesday"
            DayOfWeek.THURSDAY -> "Thursday"
            DayOfWeek.FRIDAY -> "Friday"
            DayOfWeek.SATURDAY -> "Saturday"
            DayOfWeek.SUNDAY -> "Sunday"
            else -> "Unknown"
        }
    }
    fun formatDate(): String {
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val outputFormat = DateTimeFormatter.ofPattern("MMM dd", Locale.ENGLISH)

        val localDate = LocalDate.parse(date, inputFormat)
        return localDate.format(outputFormat)
    }
}