package com.kweather.forecastpro.fragments

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.kweather.forecastpro.MainViewModel
import com.kweather.forecastpro.adapters.ViewPageAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.tabs.TabLayoutMediator
import com.kweather.forecastpro.R
import com.kweather.forecastpro.databinding.FragmentMainBinding
import com.squareup.picasso.Picasso

var userInput: String = ""

class MainFragment : Fragment() {
    private lateinit var fLocationClient: FusedLocationProviderClient
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var geoCity: String
    private lateinit var sharedPreferences: SharedPreferences

    private val listOfFragments = listOf(
        DaysFragment.newInstance(),
        HoursFragment.newInstance()
    )
    private val listOfTabs = listOf(
        "3 DAYS FORECAST",
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
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        geoCity = getSavedCityFromSharedPreferences(sharedPreferences).toString()
        if (geoCity.isEmpty()){
            binding.dialogSearchCity.visibility = View.VISIBLE
            binding.weatherDialogIcon.visibility = View.VISIBLE
            binding.buttonToCloseDialog.visibility = View.GONE
            binding.searchCity.setOnClickListener {
                val userInput = binding.inputSearchCity.text.toString().trim()
                if (userInput.isEmpty()){
                    Toast.makeText(
                        requireContext(),
                        "Enter your location!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    saveCityToSharedPreferences(sharedPreferences, userInput)
                    binding.dialogSearchCity.visibility = View.GONE
                    binding.weatherDialogIcon.visibility = View.GONE
                    binding.mainContainer.visibility = View.VISIBLE
                    viewModel.loadData(userInput)

                }

            }
        } else {
            viewModel.loadData(geoCity)
        }
        binding.syncButton.setOnClickListener {
            viewModel.loadData(geoCity)
        }
        attachAdapterToViewPager()
        observeWeather()
    }

    private fun saveCityToSharedPreferences(sharedPreferences: SharedPreferences, cityName: String) {
        val editor = sharedPreferences.edit()
        editor.putString("city", cityName)
        editor.apply()
    }

    private fun getSavedCityFromSharedPreferences(sharedPreferences: SharedPreferences): String? {
        return sharedPreferences.getString("city", "")
    }


    private fun observeWeather() {
        viewModel.currentDayWeather.observe(viewLifecycleOwner) {
            with(binding) {
                val lastUpdateString = getString(R.string.lastUpdate)
                val currentTemperatureString = getString(R.string.currentTemperature)
                val windKmString = getString(R.string.windKm)
                val humidityPercentString = getString(R.string.humidityPercent)
                lastUpdateDate.text = String.format(lastUpdateString, it.date)
                signPlus.visibility = if (it.currentTemperature > 0) View.VISIBLE else View.GONE
                signMinus.visibility = if (it.currentTemperature > 0) View.GONE else View.VISIBLE
                currentTemperature.text =
                    String.format(currentTemperatureString, it.currentTemperature)
                city.text = it.cityName
                condition.text = it.condition
                Picasso.get().load("https:${it.icon}").into(iconWeather)
                val maxSign = if (it.maxTemp > 0) "+" else "-"
                val minSign = if (it.minTemp > 0) "+" else "-"
                maxMinTemp.text =
                    String.format("%s%s°/%s%s°", maxSign, it.maxTemp, minSign, it.minTemp)
                wind.text = String.format(windKmString, it.wind)
                humidity.text = String.format(humidityPercentString, it.humidity)
            }
            Log.d("MainFragment", it.toString())
        }

        binding.searchButton.setOnClickListener {
            showSearchDialog()
        }

        binding.buttonToCloseDialog.setOnClickListener {
            binding.overlayView.visibility = View.GONE
            binding.dialogSearchCity.visibility = View.GONE
            binding.weatherDialogIcon.visibility = View.GONE
        }

        viewModel.isProgressBar.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.mainContainer.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.mainContainer.visibility = View.VISIBLE
            }
        }
    }

    private fun showSearchDialog() {
        binding.dialogSearchCity.visibility = View.VISIBLE
        binding.buttonToCloseDialog.visibility = View.VISIBLE
        binding.overlayView.visibility = View.VISIBLE
        binding.weatherDialogIcon.visibility = View.VISIBLE
        binding.overlayView.isClickable = true
        binding.searchCity.setOnClickListener {
            geoCity = binding.inputSearchCity.text.toString().trim()
            if (geoCity.isEmpty()) {
                Toast.makeText(requireContext(), "Enter your city!", Toast.LENGTH_LONG).show()
            } else {
                saveCityToSharedPreferences(sharedPreferences, geoCity)
                viewModel.loadData(geoCity)
                binding.syncButton.setOnClickListener {
                    viewModel.loadData(geoCity)
                }
            }
            binding.inputSearchCity.setText("")
            binding.dialogSearchCity.visibility = View.GONE
            binding.weatherDialogIcon.visibility = View.GONE
            binding.overlayView.visibility = View.GONE
        }
    }


    private fun getLocation() {
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
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token)
            .addOnCompleteListener {
                val geoCity = "${it.result.latitude},${it.result.longitude}"
                viewModel.loadData(geoCity)

                Log.d("CheckGeo", geoCity)


                binding.syncButton.setOnClickListener {
                    viewModel.loadData(geoCity)
                }
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
            if (it) {
                Toast.makeText(activity, "Доступ к местоположению получен!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(activity, "Доступ к местоположению не получен!", Toast.LENGTH_SHORT)
                    .show()
            }

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