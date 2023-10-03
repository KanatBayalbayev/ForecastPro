package com.example.forecastpro.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.forecastpro.MainViewModel
import com.example.forecastpro.R
import com.example.forecastpro.adapters.DaysAdapter
import com.example.forecastpro.adapters.HoursAdapter
import com.example.forecastpro.databinding.FragmentDaysBinding
import com.example.forecastpro.databinding.FragmentHoursBinding


class HoursFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHoursBinding
    private lateinit var hoursAdapter: HoursAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachRecyclerView()
    }


    private fun attachRecyclerView(){
        hoursAdapter = HoursAdapter(requireContext())
        binding.recyclerView.adapter = hoursAdapter
        viewModel.currentDayWeather.observe(viewLifecycleOwner){
            val list = it.listOfHours
            hoursAdapter.submitList(list)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}