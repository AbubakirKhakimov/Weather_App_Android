package com.x.a_technologies.weather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.adapters.hourlyShowDetailFragmentAdapters.HourlyShowDetailPagerAdapter
import com.x.a_technologies.weather.adapters.mainFragmentAdapters.HourlyAdapterCallBack
import com.x.a_technologies.weather.adapters.mainFragmentAdapters.HourlyDetailAdapter
import com.x.a_technologies.weather.databinding.FragmentHourlyShowDetailBinding
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Hourly

class HourlyShowDetailFragment(val hourlyWeatherList: List<Hourly>, val position:Int, val cityName:String) : Fragment(),HourlyAdapterCallBack {

    lateinit var binding: FragmentHourlyShowDetailBinding
    lateinit var RVadapter: HourlyDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHourlyShowDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cityName.text = cityName

        RVadapter = HourlyDetailAdapter(hourlyWeatherList,this, position)
        binding.RVDetails.adapter = RVadapter
        binding.RVDetails.scrollToPosition(position)

        binding.viewPager2.adapter = HourlyShowDetailPagerAdapter(hourlyWeatherList)
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.offscreenPageLimit = 2
        binding.viewPager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        binding.viewPager2.currentItem = position

        binding.viewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                RVadapter.selectPosition = position
                RVadapter.notifyDataSetChanged()
                binding.RVDetails.scrollToPosition(position)
            }
        })

        binding.backButton.setOnClickListener {
            replaceFragment(MainFragment())
        }

    }

    fun replaceFragment(fragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentConteiner,fragment).commit()
    }

    override fun clickHourly(hourlyWeatherList: List<Hourly>, position: Int) {
        binding.viewPager2.currentItem = position
    }

}