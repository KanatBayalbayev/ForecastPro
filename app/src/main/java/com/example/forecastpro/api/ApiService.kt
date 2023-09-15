package com.example.forecastpro.api

import com.example.forecastpro.pojo.WeatherData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("forecast.json")
    fun getData(
        @Query(API_KEY) api_key: String = "dd2c1ef1a2c44dc2a6733324231109",
        @Query(QUERY_CITY) city: String = "",
        @Query(QUERY_DAYS) days: String = "3",
        @Query(QUERY_AQI) aqi: String = "no",
        @Query(QUERY_ALERTS) alerts: String = "no",
        ): Single<WeatherData>

    companion object {
        private const val API_KEY = "key"
        private const val QUERY_CITY = "q"
        private const val QUERY_DAYS = "days"
        private const val QUERY_AQI = "aqi"
        private const val QUERY_ALERTS = "alerts"
    }
}