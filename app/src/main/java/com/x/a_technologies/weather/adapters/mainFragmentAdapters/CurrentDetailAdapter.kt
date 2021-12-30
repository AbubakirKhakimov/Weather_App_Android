package com.x.a_technologies.weather.adapters.mainFragmentAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.datas.CurrentDetailData
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Current
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.CurrentDetailItemLayoutBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CurrentDetailAdapter(var currentWeather:Current, val context: Context):RecyclerView.Adapter<CurrentDetailAdapter.ItemHolder>() {
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
        currentDetailList.add(CurrentDetailData(R.drawable.ic_dew,"${getIntegerNumber(currentWeather.dew_point)}°C"
            ,context.getString(R.string.dew_point)))
        currentDetailList.add(CurrentDetailData(R.drawable.humidity,"${currentWeather.humidity} %"
            ,context.getString(R.string.humidity)))
        currentDetailList.add(CurrentDetailData(R.drawable.ic_pressure,"${currentWeather.pressure} hPa"
            ,context.getString(R.string.pressure)))
        currentDetailList.add(CurrentDetailData(R.drawable.cloudy,"${currentWeather.clouds} %"
            ,context.getString(R.string.cloudiness)))
        currentDetailList.add(CurrentDetailData(R.drawable.uv_index,currentWeather.uvi.toString()
            ,context.getString(R.string.uv_index)))
        currentDetailList.add(CurrentDetailData(R.drawable.ic_visibility,"${currentWeather.visibility} m"
            ,context.getString(R.string.visibility)))
        currentDetailList.add(CurrentDetailData(R.drawable.sunrise,getTimeFormat(currentWeather.sunrise)
            ,context.getString(R.string.sunrise)))
        currentDetailList.add(CurrentDetailData(R.drawable.sunset,getTimeFormat(currentWeather.sunset)
            ,context.getString(R.string.sunset)))
    }

    fun getTimeFormat(unixTime:Int):String{
        return SimpleDateFormat("HH:mm").format(Date(unixTime*1000L))
    }

    fun getIntegerNumber(num:Double):String{
        return DecimalFormat("#").format(num)
    }
}