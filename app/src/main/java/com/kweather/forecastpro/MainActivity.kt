package com.kweather.forecastpro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.kweather.forecastpro.fragments.MainFragment
import com.kweather.forecastpro.pojo.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var fLocationClient: FusedLocationProviderClient
    private var coordinates = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchWelcomeActivity()

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()
    }

    private fun launchWelcomeActivity(){
        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val firstRun: Boolean = prefs.getBoolean(KEY_FIRST_RUN, true)

        if (firstRun) {
            startActivity(Intent(this, WelcomeActivity::class.java))
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commit()
        }
    }
    companion object{
        private val PREFS_NAME = "MyPrefsFile"
        private val KEY_FIRST_RUN = "firstRun"
    }



}