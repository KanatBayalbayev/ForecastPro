package com.kweather.forecastpro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kweather.forecastpro.adapters.HoursAdapter
import com.kweather.forecastpro.databinding.FragmentHoursBinding


class HoursFragment : Fragment() {
    private val viewModel: com.kweather.forecastpro.MainViewModel by activityViewModels()
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