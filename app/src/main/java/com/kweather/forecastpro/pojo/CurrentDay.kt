package com.kweather.forecastpro.pojo

data class CurrentDay(
    var cityName: String,
    var date: String,
    var condition: String,
    var icon: String,
    var currentTemperature: Int,
    var wind: String,
    var humidity: String,
    var maxTemp: Int,
    var minTemp: Int,
    var dayName: String,
    var dateDay: String,
    var listOfDays: List<Forecastday>,
    var listOfHours: List<Hour>

)
