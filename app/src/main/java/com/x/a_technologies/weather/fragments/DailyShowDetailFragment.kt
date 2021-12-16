package com.x.a_technologies.weather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.x.a_technologies.weather.databinding.FragmentDailyShowDetailBinding
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Daily

class DailyShowDetailFragment(val dailyWeatherList: List<Daily>, val position:Int) : Fragment() {

    lateinit var binding: FragmentDailyShowDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDailyShowDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
}