package com.x.a_technologies.weather.datas

import com.orhanobut.hawk.Hawk
import com.x.a_technologies.weather.api.API
import com.x.a_technologies.weather.datas.DatasCityLocationAPI.CityLocation
import com.x.a_technologies.weather.datas.DatasWeatherAPI.WeatherData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PublicDatas {

    var citiesList = ArrayList<CitiesData>()
    var weatherData = ArrayList<WeatherData?>()
    var pagePosition = 0

    fun writeCityList(){
        Hawk.put(Constants.citiesDataKey, citiesList)
    }

    fun readCityList(){
        citiesList = Hawk.get(Constants.citiesDataKey, ArrayList())
    }

    private var weatherApi:API? = null
    fun getWeatherApi(): API {
        if (weatherApi == null){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_WEATHER)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            weatherApi = retrofit.create(API::class.java)
        }

        return weatherApi!!
    }

    private var cityLocationApi:API? = null
    fun getCityLocationApi(): API {
        if (cityLocationApi == null){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_CITY_LOCATION)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            cityLocationApi = retrofit.create(API::class.java)
        }

        return cityLocationApi!!
    }
}