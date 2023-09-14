package com.example.forecastpro.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.forecastpro.MainViewModel
import com.example.forecastpro.R
import com.example.forecastpro.adapters.DaysAdapter
import com.example.forecastpro.adapters.HoursAdapter
import com.example.forecastpro.databinding.FragmentDaysBinding
import com.example.forecastpro.databinding.FragmentHoursBinding


class HoursFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentHoursBinding
    private lateinit var hoursAdapter: HoursAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = FragmentHoursBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        attachRecyclerView()
    }


    private fun attachRecyclerView(){
        hoursAdapter = HoursAdapter()
        binding.recyclerView.adapter = hoursAdapter
        viewModel.currentWeather.observe(viewLifecycleOwner){
            val list = it.listOfHours
            hoursAdapter.submitList(list)
            Log.d("HoursFragment", list.toString())

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}