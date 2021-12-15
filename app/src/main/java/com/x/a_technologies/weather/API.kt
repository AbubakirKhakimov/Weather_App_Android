package com.x.a_technologies.weather

import com.x.a_technologies.weather.Datas.DatasCityLocationAPI.CityLocation
import com.x.a_technologies.weather.Datas.DatasWeatherAPI.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface API {
    @GET("onecall")
    fun getWeatherData(
        @Query("exclude") exclude: String,
        @Query("appid") api_key: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): Call<WeatherData>

    @GET("autocomplete")
    fun getCityLocation(
        @Header("x-rapidapi-host") host:String,
        @Header("x-rapidapi-key") key:String,
        @Query("limit") limit: Int,
        @Query("type") type: String,
        @Query("q") q: String
    ): Call<List<CityLocation>>
}