package com.x.a_technologies.weather.adapters.mainFragmentAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.databinding.MainPagerLayoutBinding
import com.x.a_technologies.weather.datas.Constants
import com.x.a_technologies.weather.datas.DatasWeatherAPI.WeatherData
import com.x.a_technologies.weather.datas.PublicDatas
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Daily
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Hourly
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener


interface MainPagerCallBack{
    fun clickHourly(hourlyWeatherList: List<Hourly>, position:Int)
    fun clickDaily(dailyWeatherList: List<Daily>, position:Int)
}

class MainPagerAdapter(val mainPagerCallBack: MainPagerCallBack, val context: Context)
    :RecyclerView.Adapter<MainPagerAdapter.ItemHolder>(),
    HourlyAdapterCallBack,
    DailyAdapterCallBack {
    inner class ItemHolder(val binding: MainPagerLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(MainPagerLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.binding.scrollView.visibility = View.GONE

        if (PublicDatas.weatherData[position] == null) {
            connection(position, holder)
        }else{
            successfulConnected(holder, PublicDatas.weatherData[position]!!)
        }

        holder.binding.swipeRefreshLayout.setOnRefreshListener {
            connection(position,holder)
        }

        holder.binding.RVHourlyDetail.addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(true)
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })

        holder.binding.RVDailyDetail.addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(true)
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }

    override fun getItemCount(): Int {
        return PublicDatas.citiesList.size
    }

    //*-------------------------------------------------------------------------------------*\\

    fun successfulConnected(holder:ItemHolder,weatherData: WeatherData){
        //instalization adapters
        holder.binding.RVCurrentDetail.adapter = CurrentDetailAdapter(weatherData.current, context)
        holder.binding.RVHourlyDetail.adapter = HourlyDetailAdapter(weatherData.hourly,this)
        holder.binding.RVDailyDetail.adapter = DailyDetailAdapter(weatherData.daily.toMutableList(),this)

        //instalization views
        holder.binding.currentDate.text = getDateFormat(weatherData.current.dt)
        holder.binding.currentTemp.text = getIntegerNumber(weatherData.current.temp)
        holder.binding.currentDescription.text = weatherData.current.weather[0].description
        holder.binding.animationView.setAnimation("${weatherData.current.weather[0].icon}.json")
        holder.binding.animationView.playAnimation()
        holder.binding.windSpeed.text = "${weatherData.current.wind_speed} m/s"
        holder.binding.windGust.text = "${weatherData.current.wind_gust} m/s"

        holder.binding.scrollView.visibility = View.VISIBLE
        holder.binding.swipeRefreshLayout.isRefreshing = false
    }

    fun failureConnected(holder:ItemHolder){
        holder.binding.swipeRefreshLayout.isRefreshing = false
        Toast.makeText(context, "No internet connection!", Toast.LENGTH_SHORT).show()
    }

    fun connection(position: Int, holder:ItemHolder){
        holder.binding.swipeRefreshLayout.isRefreshing = true

        PublicDatas.getWeatherApi().getWeatherData(
            Constants.exclude,
            Constants.API_KEY_WEATHER,
            PublicDatas.citiesList[position].lat,
            PublicDatas.citiesList[position].lon,
            Constants.units,
            Constants.language).enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if (response.isSuccessful){
                    PublicDatas.weatherData[position] = response.body()!!
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

    fun getIntegerNumber(num:Double):String{
        return DecimalFormat("#").format(num)
    }

    fun getDateFormat(unixTime:Int):String{
        return SimpleDateFormat("E, dd MMMM").format(Date(unixTime*1000L))
    }

    override fun clickDaily(dailyWeatherList: List<Daily>, position: Int) {
        mainPagerCallBack.clickDaily(dailyWeatherList, position)
    }

    override fun clickHourly(hourlyWeatherList: List<Hourly>, position: Int) {
        mainPagerCallBack.clickHourly(hourlyWeatherList, position)
    }
}