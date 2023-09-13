package com.example.forecastpro.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.forecastpro.MainViewModel
import com.example.forecastpro.R
import com.example.forecastpro.adapters.ViewPageAdapter
import com.example.forecastpro.adapters.WeatherAdapter
import com.example.forecastpro.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var weatherAdapter: WeatherAdapter

    private val listOfFragments = listOf(
        DaysFragment.newInstance(),
        HoursFragment.newInstance()
    )
    private val listOfTabs = listOf(
        "DAYS",
        "HOURS"
    )

    private lateinit var binding: FragmentMainBinding
    private lateinit var pLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        attachAdapterToViewPager()
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            with(binding) {
                date.text = it.date
                city.text = it.cityName
                condition.text = it.condition
                Picasso.get().load("https:${it.icon}").into(iconWeather)

                currentTemp.text = "${it.currentTemperature}°"

                wind.text = "${it.wind}km/h"
                humidity.text = "${it.humidity}%"
            }

        }
//        viewModel.listDays.observe(viewLifecycleOwner) {
//            Log.d("MainFragment", it.toString())
//        }
    }


    private fun attachAdapterToViewPager() {
        val adapter = ViewPageAdapter(activity as FragmentActivity, listOfFragments)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = listOfTabs[position]
        }.attach()
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}