package com.x.a_technologies.weather.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.Datas.DatasWeatherAPI.Hourly
import com.x.a_technologies.weather.databinding.HourlyDailyDetailItemLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class HourlyDetailAdapter(val hourlyWeatherList:List<Hourly>):RecyclerView.Adapter<HourlyDetailAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: HourlyDailyDetailItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(HourlyDailyDetailItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = hourlyWeatherList[position]
        holder.binding.animationView.setAnimation("${item.weather[0].icon}.json")
        holder.binding.timeOrDate.text = getTimeFormat(item.dt)
        holder.binding.temp.text = item.temp.toInt().toString()
    }

    override fun getItemCount(): Int {
        return 24
    }

    fun getTimeFormat(unixTime:Int):String{
        return SimpleDateFormat("HH:mm").format(Date(unixTime*1000L))
    }
}