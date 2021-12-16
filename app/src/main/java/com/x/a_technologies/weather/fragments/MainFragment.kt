package com.x.a_technologies.weather.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.x.a_technologies.weather.datas.CitiesData
import com.x.a_technologies.weather.datas.SettingsData
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.FragmentMainBinding
import java.util.*
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.x.a_technologies.weather.adapters.mainFragmentAdapters.MainPagerAdapter
import com.x.a_technologies.weather.adapters.mainFragmentAdapters.MainPagerCallBack
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Daily
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Hourly


class MainFragment : Fragment(), MainPagerCallBack {

    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        read()

        binding.viewPager.adapter = MainPagerAdapter(this)
        binding.viewPager.clipToPadding = false
        binding.viewPager.clipChildren = false
        binding.viewPager.offscreenPageLimit = 1
        binding.viewPager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        binding.viewPager.reduceDragSensitivity()

        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.cityName.text = SettingsData.citiesList[position].cityName
            }
        })

        binding.search.setOnClickListener {
            replaceFragment(EditLocationFragment())
        }

    }

    fun ViewPager2.reduceDragSensitivity() {
        val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true
        val recyclerView = recyclerViewField.get(this) as RecyclerView

        val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopField.isAccessible = true
        val touchSlop = touchSlopField.get(recyclerView) as Int
        touchSlopField.set(recyclerView, touchSlop*2)
    }

    fun read(){
        val gson = Gson()
        val pref = requireActivity().getSharedPreferences(SettingsData.settingsFileName,MODE_PRIVATE)
        val readedValue = pref.getString(SettingsData.citiesDataKey, "empty")

        if (readedValue == "empty"){
            SettingsData.citiesList.add(CitiesData(
                "Fergana",
                "Fergana",
                "Uzbekistan",
                41.2646,
                71.7843))
        }else{
            SettingsData.citiesList = gson.fromJson(readedValue,object : TypeToken<ArrayList<CitiesData>>() {}.type)
        }
    }

    fun replaceFragment(fragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentConteiner,fragment).commit()
    }

    override fun clickHourly(hourlyWeatherList: List<Hourly>, position: Int) {
        replaceFragment(HourlyShowDetailFragment(hourlyWeatherList,
            position, SettingsData.citiesList[binding.viewPager.currentItem].cityName))
    }

    override fun clickDaily(dailyWeatherList: List<Daily>, position: Int) {
        replaceFragment(DailyShowDetailFragment(dailyWeatherList, position))
    }

}