package com.example.forecastpro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forecastpro.api.ApiClient
import com.example.forecastpro.pojo.CurrentDay
import com.example.forecastpro.pojo.DayWeather
import com.example.forecastpro.pojo.Days
import com.example.forecastpro.pojo.Forecastday
import com.example.forecastpro.pojo.Hour
import com.example.forecastpro.pojo.WeatherData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import java.util.Locale

class MainViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _currentWeather = MutableLiveData<CurrentDay>()
    val currentWeather: LiveData<CurrentDay>
        get() = _currentWeather

    private val _currentDayWeather = MutableLiveData<DayWeather>()
    val currentDayWeather: LiveData<DayWeather>
        get() = _currentDayWeather

     val daysWeather = MutableLiveData<List<Days>>()
//    val daysWeather: MutableLiveData<List<Days>>
//        get() = _daysWeather

    init {
        loadData("Almaty")
    }

    fun loadData(geo: String?) {
        val disposable = geo?.let { it ->
            ApiClient.getApiService().getData(city = it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _currentDayWeather.value = getDayWeather(it)
                    daysWeather.value = getDays(it)

//                    Log.d("MainViewModel", it.toString())
                    Log.d("MainViewModel", getDays(it).toString())
                }, {
                    Log.d("MainViewModel", it.message.toString())
                })
        }
        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }

    private fun getDayWeather(data: WeatherData): DayWeather {
        val city = data.location.name
        val currentDate = formatDate(data.location.localtime)
        val currentTemp = data.current.tempC.toInt().toString()
        val condition = data.current.condition.text
        val icon = data.current.condition.icon
        val wind = data.current.windKph.toString()
        val humidity = data.current.humidity.toString()
        return DayWeather(city, currentDate, currentTemp, condition, icon, wind, humidity)
    }

    private fun getDays(data: WeatherData): List<Days> {
        val listOfDays = data.forecast.forecastday
        val list = ArrayList<Days>()
        for (element in listOfDays) {
            val dayName = element.date
            val date = element.date
            val temperature = element.day.maxtempC.toInt().toString()
            val icon = element.day.condition.icon
            list.add(
                Days(dayName, date, temperature, icon)
            )
        }
        return list
    }


    private fun getCurrentDay(data: WeatherData): CurrentDay {
        val city = data.location.name
        val currentDate = formatDate(data.location.localtime)
        val currentTemp = data.current.tempC.toInt().toString()
        val condition = data.current.condition.text
        val icon = data.current.condition.icon
        val wind = data.current.windKph.toString()
        val humidity = data.current.humidity.toString()
        val dayName = "getDayOfWeek(data.location.localtime)"
        val dateDay = "formatDateString(data.location.localtime)"
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
            listHours
        )
    }

    private fun getListOfHours(data: WeatherData): List<Hour> {
        val listOfHours = data.forecast.forecastday[0]
        return listOfHours.hour
    }

    private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        val outputFormat = SimpleDateFormat("MMM dd", Locale.US)
        val dateRes = inputFormat.parse(inputDate)
        val dayAndMonth = dateRes?.let { outputFormat.format(it) }.toString()
        val month = getMonth(dayAndMonth)
        val hours = getHours(inputDate)
        return "${month}, $hours"
    }

    private fun getHours(inputDate: String): String {
        val parts = inputDate.split(" ")
        return parts.getOrNull(1).toString()
    }

    private fun getMonth(inputDate: String): String {
        val parts = inputDate.split(" ")
        val month = parts.getOrNull(0).toString()
        val day = parts.getOrNull(1).toString()
        return "$month $day"
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}