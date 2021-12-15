package com.x.a_technologies.weather.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.Datas.DatasWeatherAPI.Daily
import com.x.a_technologies.weather.databinding.HourlyDailyDetailItemLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class DailyDetailAdapter(val dailyWeatherList: MutableList<Daily>):RecyclerView.Adapter<DailyDetailAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: HourlyDailyDetailItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(HourlyDailyDetailItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = dailyWeatherList[position]
        holder.binding.animationView.setAnimation("${item.weather[0].icon}.json")
        holder.binding.timeOrDate.text = getDateFormat(item.dt)
        holder.binding.temp.text = item.temp.day.toInt().toString()
    }

    override fun getItemCount(): Int {
        return dailyWeatherList.size
    }

    fun getDateFormat(unixTime:Int):String{
        return SimpleDateFormat("dd.MM").format(Date(unixTime*1000L))
    }

    init {
        dailyWeatherList.removeAt(0)
    }
}