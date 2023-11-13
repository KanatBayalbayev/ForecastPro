package com.kweather.forecastpro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.kweather.forecastpro.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var fLocationClient: FusedLocationProviderClient
    private var coordinates = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()
    }

}