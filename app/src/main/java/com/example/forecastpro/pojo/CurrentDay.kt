package com.example.forecastpro.pojo

data class CurrentDay(
    var cityName: String? = null,
    var date: String? = null,
    var condition: String? = null,
    var icon: String? = null,
    var currentTemperature: String? = null,
    var wind: String? = null,
    var humidity: String? = null,
    var maxTemp: String? = null,
    var dayName: String? = null,
    var dateDay: String? = null,
    var listOfDays: List<Forecastday>? = null,
    var listOfHours: List<Hour>? = null

)
