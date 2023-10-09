package com.example.forecastpro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.forecastpro.fragments.MainFragment
import com.example.forecastpro.fragments.WelcomeFragment
import com.google.android.gms.location.FusedLocationProviderClient

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