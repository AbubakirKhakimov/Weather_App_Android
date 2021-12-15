package com.x.a_technologies.weather.Fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.x.a_technologies.weather.API
import com.x.a_technologies.weather.Adapters.*
import com.x.a_technologies.weather.Datas.CitiesData
import com.x.a_technologies.weather.Datas.DatasWeatherAPI.WeatherData
import com.x.a_technologies.weather.Datas.SettingsData
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback


class MainFragment : Fragment(),ViewPagerCallBack {

    lateinit var binding: FragmentMainBinding
    lateinit var api: API
    lateinit var weatherData: WeatherData

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

        binding.viewPager.adapter = ViewPagerAdapter(this)
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

    override fun setFragment(fragment:Fragment) {
        replaceFragment(fragment)
    }

}