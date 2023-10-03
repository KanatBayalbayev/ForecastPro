package com.example.forecastpro.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.forecastpro.MainViewModel
import com.example.forecastpro.R
import com.example.forecastpro.adapters.DaysAdapter
import com.example.forecastpro.adapters.HoursAdapter
import com.example.forecastpro.databinding.FragmentDaysBinding
import com.example.forecastpro.databinding.FragmentDetailBinding
import com.example.forecastpro.pojo.Forecastday
import com.example.forecastpro.pojo.Hour


class DetailFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailBinding
    private lateinit var hoursAdapter: HoursAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hoursAdapter = HoursAdapter(requireContext())

        viewModel.objForAddition.observe(viewLifecycleOwner) {
            val list = it.hour
            hoursAdapter.submitList(list)

            binding.dayName.text = it.getDayOfWeek()
            binding.monthDay.text = it.formatMonthAndDay()
            binding.sunrise.text = it.astro.sunrise
            binding.sunset.text = it.astro.sunset

        }
        binding.detailRecyclerView.adapter = hoursAdapter
        attachRecyclerView()
        binding.buttonToBack.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.remove(this)
                ?.replace(R.id.container, MainFragment.newInstance())
                ?.commit()

        }
    }

    private fun attachRecyclerView() {

    }


    companion object {
        @JvmStatic
        fun newInstance() = DetailFragment()
    }
}