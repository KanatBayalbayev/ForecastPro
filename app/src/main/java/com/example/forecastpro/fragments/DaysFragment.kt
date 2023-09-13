package com.example.forecastpro.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.forecastpro.MainViewModel
import com.example.forecastpro.R
import com.example.forecastpro.adapters.DayModel
import com.example.forecastpro.adapters.WeatherAdapter
import com.example.forecastpro.databinding.FragmentDaysBinding


class DaysFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentDaysBinding
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = FragmentDaysBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachRecyclerView()
    }

    private fun attachRecyclerView(){
        weatherAdapter = WeatherAdapter()
        binding.recyclerView.adapter = weatherAdapter
        viewModel.currentWeather.observe(viewLifecycleOwner){
            val list = it.listOfDays
            weatherAdapter.submitList(list)
        }
    }



    companion object {

        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}