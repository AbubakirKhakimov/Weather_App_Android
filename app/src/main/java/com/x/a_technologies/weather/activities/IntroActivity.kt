package com.x.a_technologies.weather.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.orhanobut.hawk.Hawk
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.ActivityIntroBinding
import com.x.a_technologies.weather.datas.Constants
import com.x.a_technologies.weather.datas.DatasWeatherAPI.WeatherData
import com.x.a_technologies.weather.datas.PublicDatas
import com.x.a_technologies.weather.fragments.AddCityFragment
import com.x.a_technologies.weather.fragments.IntroFragment
import com.x.a_technologies.weather.utils.LocaleManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class IntroActivity : AppCompatActivity() {

    lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PublicDatas.readCityList()
        addNullsToList()
        setLanguageApi()

        if (PublicDatas.citiesList.size == 0){
            replaceFragment(AddCityFragment())
        }else{
            replaceFragment(IntroFragment())
        }

    }

    fun setLanguageApi(){
        val lan = Hawk.get("pref_lang", "empty")
        if (lan == "empty"){
            val locale = Locale.getDefault().language
            if (locale == "ru"){
                Constants.language = locale
            }else{
                Constants.language = "en"
            }
        }else{
            Constants.language = lan
        }
    }

    fun addNullsToList(){
        repeat(PublicDatas.citiesList.size){
            PublicDatas.weatherData.add(null)
        }
    }

    fun replaceFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentConteinerIntro,fragment).commit()
    }

    override fun attachBaseContext(newBase: Context?) {
        Hawk.init(newBase).build()
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }

}