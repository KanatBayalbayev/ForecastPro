package com.kweather.forecastpro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kweather.forecastpro.OnItemClickListener
import com.kweather.forecastpro.R
import com.kweather.forecastpro.adapters.DaysAdapter
import com.kweather.forecastpro.databinding.FragmentDaysBinding
import com.kweather.forecastpro.pojo.Forecastday


class DaysFragment : Fragment(){
    private val viewModel: com.kweather.forecastpro.MainViewModel by activityViewModels()
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
        daysAdapter = DaysAdapter(object : OnItemClickListener{
            override fun onItemClick(item: Forecastday) {
                viewModel.objForAddition.value = item
                navigateToNextFragment()
            }
        }, requireContext())
        binding.recyclerView.adapter = daysAdapter
        viewModel.currentDayWeather.observe(viewLifecycleOwner){
            val list = it.listOfDays
            daysAdapter.submitList(list)
        }
    }

    private fun navigateToNextFragment(){
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, DetailFragment.newInstance())
            ?.addToBackStack(null)
            ?.commit()
    }



    companion object {

        @JvmStatic
        fun newInstance() = DaysFragment()
    }


}