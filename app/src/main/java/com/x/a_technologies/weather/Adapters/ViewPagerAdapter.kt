package com.x.a_technologies.weather.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.API
import com.x.a_technologies.weather.Datas.DatasWeatherAPI.WeatherData
import com.x.a_technologies.weather.Datas.SettingsData
import com.x.a_technologies.weather.databinding.ViewPagerItemLayoutBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

interface ViewPagerCallBack{
    fun setFragment(fragment:Fragment)
}

class ViewPagerAdapter(val viewPagerCallBack: ViewPagerCallBack):RecyclerView.Adapter<ViewPagerAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: ViewPagerItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    lateinit var api: API

    init {
        instalizationURL()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(ViewPagerItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.binding.scrollView.visibility = View.GONE
        connection(position,holder)

        holder.binding.swipeRefreshLayout.setOnRefreshListener {
            connection(position,holder)
        }
    }

    override fun getItemCount(): Int {
        return SettingsData.citiesList.size
    }

    //*-------------------------------------------------------------------------------------*\\

    fun successfulConnected(holder:ItemHolder,weatherData: WeatherData){
        //instalization adapters
        holder.binding.RVCurrentDetail.adapter = CurrentDetailAdapter(weatherData.current)
        holder.binding.RVHourlyDetail.adapter = HourlyDetailAdapter(weatherData.hourly)
        holder.binding.RVDailyDetail.adapter = DailyDetailAdapter(weatherData.daily.toMutableList())

        //instalization views
        holder.binding.currentDate.text = getDateFormat(weatherData.current.dt)
        holder.binding.currentTemp.text = weatherData.current.temp.toInt().toString()
        holder.binding.currentDescription.text = weatherData.current.weather[0].description
        holder.binding.animationView.setAnimation("${weatherData.current.weather[0].icon}.json")
        holder.binding.windSpeed.text = "${weatherData.current.wind_speed} m/s"
        holder.binding.windGust.text = "${weatherData.current.wind_gust} m/s"

        holder.binding.scrollView.visibility = View.VISIBLE
        holder.binding.swipeRefreshLayout.isRefreshing = false
    }

    fun failureConnected(holder:ItemHolder){
        holder.binding.swipeRefreshLayout.isRefreshing = false
    }

    fun connection(position: Int,holder:ItemHolder){
        holder.binding.swipeRefreshLayout.isRefreshing = true

        api.getWeatherData(
            SettingsData.exclude,
            SettingsData.API_KEY_WEATHER,
            SettingsData.citiesList[position].lat,
            SettingsData.citiesList[position].lon,
            SettingsData.units,
            SettingsData.language).enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if (response.isSuccessful){
                    successfulConnected(holder,response.body()!!)
                }else{
                    failureConnected(holder)
                }
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                failureConnected(holder)
            }

        })
    }

    fun instalizationURL(){
        val retrofit = Retrofit.Builder()
            .baseUrl(SettingsData.BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(API::class.java)
    }

    fun getDateFormat(unixTime:Int):String{
        return SimpleDateFormat("E, dd MMMM", Locale.ENGLISH).format(Date(unixTime*1000L))
    }
}