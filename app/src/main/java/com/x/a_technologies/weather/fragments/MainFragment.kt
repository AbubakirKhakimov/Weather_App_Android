package com.x.a_technologies.weather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.datas.PublicDatas
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.FragmentMainBinding
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

        binding.viewPager.adapter = MainPagerAdapter(this, requireActivity())
        binding.viewPager.clipToPadding = false
        binding.viewPager.clipChildren = false
        binding.viewPager.offscreenPageLimit = 1
        binding.viewPager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        binding.viewPager.postDelayed({
            binding.viewPager.setCurrentItem(PublicDatas.pagePosition, false)
            PublicDatas.pagePosition = 0
        }, 1)

        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.cityName.text = PublicDatas.citiesList[position].cityName
            }
        })

        binding.search.setOnClickListener {
            replaceFragment(EditLocationFragment())
        }

        binding.setLanguage.setOnClickListener {
            SetLanguageFragment().show(requireActivity().supportFragmentManager, tag)
        }

    }

    fun replaceFragment(fragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentConteiner,fragment).commit()
    }

    override fun clickHourly(hourlyWeatherList: List<Hourly>, position: Int) {
        replaceFragment(HourlyShowDetailFragment(hourlyWeatherList, position
            ,binding.viewPager.currentItem))
    }

    override fun clickDaily(dailyWeatherList: List<Daily>, position: Int) {
        replaceFragment(DailyShowDetailFragment(dailyWeatherList, position
            ,binding.viewPager.currentItem))
    }

}