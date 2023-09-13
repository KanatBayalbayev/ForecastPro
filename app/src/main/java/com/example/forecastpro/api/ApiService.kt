package com.example.forecastpro.api

import retrofit2.http.Query

interface ApiService {

    @Query("forecast.json")
    fun getData() :
}