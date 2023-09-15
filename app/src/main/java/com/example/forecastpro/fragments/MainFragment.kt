package com.example.forecastpro.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.forecastpro.MainViewModel
import com.example.forecastpro.OnItemClickListener
import com.example.forecastpro.adapters.ViewPageAdapter
import com.example.forecastpro.adapters.DaysAdapter
import com.example.forecastpro.databinding.FragmentMainBinding
import com.example.forecastpro.pojo.Forecastday
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class MainFragment : Fragment() {
    private lateinit var fLocationClient: FusedLocationProviderClient
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var geoCity: String

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
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        attachAdapterToViewPager()
        observeWeather()
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

//        getLocation()



    }

    private fun observeWeather(){
        viewModel.currentDayWeather.observe(viewLifecycleOwner) {
            with(binding) {
                date.text = it.date
                city.text = it.cityName
                condition.text = it.condition
                Picasso.get().load("https:${it.icon}").into(iconWeather)

                currentTemp.text = "${it.currentTemperature}°"

                wind.text = "${it.wind}"
                humidity.text = "${it.humidity}"
            }
            Log.d("MainFragment", it.toString())
        }
        binding.syncButton.setOnClickListener {
            viewModel.loadData("Oslo")
        }

    }


    private fun getLocation(){
        val ct = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token)
            .addOnCompleteListener{
                geoCity = "${it.result.latitude},${it.result.longitude}"
                Log.d("Main",  "${it.result.latitude},${it.result.longitude}")
            }
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