package com.x.a_technologies.weather.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.adapters.hourlyShowDetailFragmentAdapters.HourlyShowDetailPagerAdapter
import com.x.a_technologies.weather.adapters.mainFragmentAdapters.HourlyAdapterCallBack
import com.x.a_technologies.weather.adapters.mainFragmentAdapters.HourlyDetailAdapter
import com.x.a_technologies.weather.databinding.FragmentHourlyShowDetailBinding
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Hourly
import com.x.a_technologies.weather.datas.PublicDatas
import java.text.SimpleDateFormat
import java.util.*

class HourlyShowDetailFragment(val hourlyWeatherList: List<Hourly>, val position:Int, val pagePosition:Int) : Fragment() {

    lateinit var binding: FragmentHourlyShowDetailBinding

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
        binding.cityName.text = PublicDatas.citiesList[pagePosition].cityName

        binding.viewPager2.adapter = HourlyShowDetailPagerAdapter(hourlyWeatherList, requireActivity())
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.offscreenPageLimit = 2
        binding.viewPager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

        val tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager2){ tab, position ->
            tab.text = getTimeFormat(hourlyWeatherList[position].dt)
        }
        tabLayoutMediator.attach()

        binding.viewPager2.postDelayed({
            binding.viewPager2.currentItem = position
        },1)

        binding.backButton.setOnClickListener {
            PublicDatas.pagePosition = pagePosition
            replaceFragment(MainFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                PublicDatas.pagePosition = pagePosition
                replaceFragment(MainFragment())
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)

    }

    fun replaceFragment(fragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentConteiner,fragment).commit()
    }

    fun getTimeFormat(unixTime:Int):String{
        return SimpleDateFormat("HH:mm").format(Date(unixTime*1000L))
    }

}