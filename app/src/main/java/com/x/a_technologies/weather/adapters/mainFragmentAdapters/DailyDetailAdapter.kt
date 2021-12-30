package com.x.a_technologies.weather.adapters.mainFragmentAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Daily
import com.x.a_technologies.weather.databinding.HourlyDailyDetailItemLayoutBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

interface DailyAdapterCallBack{
    fun clickDaily(dailyWeatherList: List<Daily>, position:Int)
}

class DailyDetailAdapter(val dailyWeatherList: MutableList<Daily>, val dailyAdapterCallBack: DailyAdapterCallBack):RecyclerView.Adapter<DailyDetailAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: HourlyDailyDetailItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(HourlyDailyDetailItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = dailyWeatherList[position]
        holder.binding.animationView.setAnimation("${item.weather[0].icon}.json")
        holder.binding.timeOrDate.text = getDateFormat(item.dt)
        holder.binding.temp.text = getIntegerNumber(item.temp.day)

        holder.binding.clickableLayout.setOnClickListener {
            dailyAdapterCallBack.clickDaily(dailyWeatherList, position)
        }
    }

    override fun getItemCount(): Int {
        return dailyWeatherList.size
    }

    fun getIntegerNumber(num:Double):String{
        return DecimalFormat("#").format(num)
    }

    fun getDateFormat(unixTime:Int):String{
        return SimpleDateFormat("dd.MM").format(Date(unixTime*1000L))
    }

    init {
        dailyWeatherList.removeAt(0)
    }
}