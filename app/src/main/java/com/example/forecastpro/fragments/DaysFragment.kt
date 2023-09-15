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
import com.example.forecastpro.OnItemClickListener
import com.example.forecastpro.adapters.DaysAdapter
import com.example.forecastpro.databinding.FragmentDaysBinding
import com.example.forecastpro.pojo.Forecastday


class DaysFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentDaysBinding
    private lateinit var daysAdapter: DaysAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachRecyclerView()
    }

    private fun attachRecyclerView(){
        daysAdapter = DaysAdapter()
        binding.recyclerView.adapter = daysAdapter
        viewModel.currentDayWeather.observe(viewLifecycleOwner){
            val list = it.listOfDays
            daysAdapter.submitList(list)

            Log.d("DaysFragment", it.toString())


        }

    }



    companion object {

        @JvmStatic
        fun newInstance() = DaysFragment()
    }

}