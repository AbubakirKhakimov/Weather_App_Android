package com.x.a_technologies.weather.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.activities.MainActivity
import com.x.a_technologies.weather.databinding.FragmentIntroBinding
import com.x.a_technologies.weather.datas.Constants
import com.x.a_technologies.weather.datas.DatasWeatherAPI.WeatherData
import com.x.a_technologies.weather.datas.PublicDatas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IntroFragment : Fragment() {

    lateinit var binding: FragmentIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIntroBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connection()
    }

    fun connection(){
        PublicDatas.getWeatherApi().getWeatherData(
            Constants.exclude,
            Constants.API_KEY_WEATHER,
            PublicDatas.citiesList[0].lat,
            PublicDatas.citiesList[0].lon,
            Constants.units,
            Constants.language).enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if (response.isSuccessful){
                    PublicDatas.weatherData[0] = response.body()!!
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                }else{
                    replaceFragment(NotConnectionFragment())
                }
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                replaceFragment(NotConnectionFragment())
            }

        })
    }

    fun replaceFragment(fragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentConteinerIntro,fragment).commit()
    }

}