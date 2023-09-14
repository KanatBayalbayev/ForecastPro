package com.example.forecastpro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forecastpro.api.ApiClient
import com.example.forecastpro.pojo.CurrentDay
import com.example.forecastpro.pojo.Forecastday
import com.example.forecastpro.pojo.Hour
import com.example.forecastpro.pojo.WeatherData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainViewModel: ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _currentWeather = MutableLiveData<CurrentDay>()
    val currentWeather: LiveData<CurrentDay>
        get() = _currentWeather

    init {
        loadData()
    }
    private fun loadData(){
        val disposable = ApiClient.getApiService().getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _currentWeather.value = getCurrentDay(it)

                Log.d("MainViewModel", it.toString())
            },{
                Log.d("MainViewModel", it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    private fun getCurrentDay(data: WeatherData): CurrentDay {
        val city = data.location.name
        val currentDate = formatDate(data.location.localtime)
        val currentTemp = data.current.tempC.toInt().toString()
        val condition = data.current.condition.text
        val icon = data.current.condition.icon
        val wind = data.current.windKph.toString()
        val humidity = data.current.humidity.toString()
        val dayName = getDayOfWeek(data.location.localtime)
        val dateDay = formatDateString(data.location.localtime)
        val listDays = data.forecast.forecastday
        val listHours = getListOfHours(data)
        return CurrentDay(
            city,
            currentDate,
            condition,
            icon,
            currentTemp,
            wind,
            humidity,
            "",
            dayName,
            dateDay,
            listDays,
        )
    }

    private fun getListOfHours(data: WeatherData): List<Hour> {
        val listOfHours = data.forecast.forecastday[0]
        return listOfHours.hour
    }

    private fun formatDate(inputDate: String): String {
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val outputFormat = DateTimeFormatter.ofPattern("MMM dd, HH:mm", Locale.ENGLISH)

        val dateTime = LocalDateTime.parse(inputDate, inputFormat)
        val zoneId = ZoneId.systemDefault()

        val zonedDateTime = dateTime.atZone(zoneId)
        return zonedDateTime.format(outputFormat)
    }
    private fun getDayOfWeek(inputDate: String): String {
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH)

        val dateTime = LocalDateTime.parse(inputDate, inputFormat)

        return when (dateTime.dayOfWeek) {
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
    private fun formatDateString(inputDate: String): String {
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val outputFormat = DateTimeFormatter.ofPattern("MMM dd", Locale.ENGLISH)

        val dateTime = LocalDateTime.parse(inputDate, inputFormat)
        return dateTime.format(outputFormat)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}