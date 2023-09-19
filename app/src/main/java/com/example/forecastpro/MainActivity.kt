package com.example.forecastpro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.forecastpro.adapters.HoursAdapter
import com.example.forecastpro.databinding.ActivityMainBinding
import com.example.forecastpro.fragments.DetailFragment
import com.example.forecastpro.fragments.HoursFragment
import com.example.forecastpro.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()


    }
}