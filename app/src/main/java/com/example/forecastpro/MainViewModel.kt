package com.example.forecastpro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val _currentWeather = MutableLiveData<String>()
    val currentWeather: LiveData<String>
        get() = _currentWeather

    private val _listWeather = MutableLiveData<String>()
    val listWeather: LiveData<String>
        get() = _listWeather
}