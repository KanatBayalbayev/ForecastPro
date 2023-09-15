package com.example.forecastpro

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forecastpro.api.ApiClient
import com.example.forecastpro.pojo.CurrentDay
import com.example.forecastpro.pojo.WeatherData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Locale

class MainViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val currentDayWeather = MutableLiveData<CurrentDay>()
    val isProgressBar = MutableLiveData<Boolean>()


    init {
        loadData("Almaty")
    }

    fun loadData(geo: String?) {
        val disposable = geo?.let { it ->
            ApiClient.getApiService().getData(city = it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isProgressBar.value = true }
                .doAfterTerminate { isProgressBar.value = false }
                .doOnError { isProgressBar.value = true }
                .subscribe({
                    currentDayWeather.value = getCurrentDay(it)
                }, {
                    Log.d("MainViewModel", it.message.toString())
                })
        }
        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }


    private fun getCurrentDay(data: WeatherData): CurrentDay {
        val city = data.location.name
        val currentDate = formatDate(data.current.lastUpdated)
        val currentTemp = data.current.tempC.toInt()
        val condition = data.current.condition.text
        val icon = data.current.condition.icon
        val wind = data.current.windKph.toString()
        val humidity = data.current.humidity.toString()
        val maxTemp = data.forecast.forecastday[0].day.maxtempC.toInt()
        val minTemp = data.forecast.forecastday[0].day.mintempC.toInt()
        val dayName = "getDayOfWeek(data.location.localtime)"
        val dateDay = "formatDateString(data.location.localtime)"
        val listDays = data.forecast.forecastday
        val listHours = data.forecast.forecastday[0].hour

        return CurrentDay(
            city,
            currentDate,
            condition,
            icon,
            currentTemp,
            wind,
            humidity,
            maxTemp,
            minTemp,
            dayName,
            dateDay,
            listDays,
            listHours
        )
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