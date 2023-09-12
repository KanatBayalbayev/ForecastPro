package com.example.forecastpro.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.forecastpro.R
import com.example.forecastpro.adapters.DayModel
import com.example.forecastpro.adapters.WeatherAdapter
import com.example.forecastpro.databinding.FragmentDaysBinding


class DaysFragment : Fragment() {

    private lateinit var binding: FragmentDaysBinding
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        val list = listOf(
            DayModel(
                "",
                "Sep, 12",
                "Tuesday",
                "",
                "",
                "25",
                "",
                "",
                ""
            ),
            DayModel(
                "",
                "Sep, 13",
                "Wednesday",
                "",
                "",
                "35",
                "",
                "",
                ""
            ),
            DayModel(
                "",
                "Sep, 14",
                "Thursday",
                "",
                "",
                "20",
                "",
                "",
                ""
            ),
        )
        weatherAdapter.submitList(list)
    }



    companion object {

        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}