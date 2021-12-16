package com.x.a_technologies.weather.adapters.mainFragmentAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.datas.CurrentDetailData
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Current
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.CurrentDetailItemLayoutBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CurrentDetailAdapter(var currentWeather:Current):RecyclerView.Adapter<CurrentDetailAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: CurrentDetailItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    var currentDetailList = ArrayList<CurrentDetailData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CurrentDetailItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = currentDetailList[position]
        holder.binding.icon.setImageResource(item.icon)
        holder.binding.info.text = item.info
        holder.binding.title.text = item.title
    }

    override fun getItemCount(): Int {
        return currentDetailList.size
    }

    init{
        currentDetailList.add(CurrentDetailData(R.drawable.ic_dew,"${currentWeather.dew_point.toInt()}°C","Dew point"))
        currentDetailList.add(CurrentDetailData(R.drawable.humidity,"${currentWeather.humidity} %","Humidity"))
        currentDetailList.add(CurrentDetailData(R.drawable.ic_pressure,"${currentWeather.pressure} hPa","Pressure"))
        currentDetailList.add(CurrentDetailData(R.drawable.cloudy,"${currentWeather.clouds} %","Cloudiness"))
        currentDetailList.add(CurrentDetailData(R.drawable.uv_index,currentWeather.uvi.toString(),"UV index"))
        currentDetailList.add(CurrentDetailData(R.drawable.ic_visibility,"${currentWeather.visibility} m","Visibility"))
        currentDetailList.add(CurrentDetailData(R.drawable.sunrise,getTimeFormat(currentWeather.sunrise),"Sunrise"))
        currentDetailList.add(CurrentDetailData(R.drawable.sunset,getTimeFormat(currentWeather.sunset),"Sunset"))
    }

    fun getTimeFormat(unixTime:Int):String{
        return SimpleDateFormat("HH:mm").format(Date(unixTime*1000L))
    }
}