package com.x.a_technologies.weather.adapters.dailyShowDetailFragmentAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.HourlyDailyShowDetailItemLayoutBinding
import com.x.a_technologies.weather.datas.CurrentDetailData
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Daily
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DailyShowDetailAdapter (val dailyData:Daily, val context: Context):RecyclerView.Adapter<DailyShowDetailAdapter.ItemHolder>(){
    inner class ItemHolder(val binding: HourlyDailyShowDetailItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    var detailList = ArrayList<CurrentDetailData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(HourlyDailyShowDetailItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = detailList[position]
        holder.binding.icon.setImageResource(item.icon)
        holder.binding.info.text = item.info
        holder.binding.title.text = item.title
    }

    override fun getItemCount(): Int {
        return detailList.size
    }

    init{
        detailList.add(CurrentDetailData(R.drawable.ic_pressure,"${dailyData.pressure} hPa"
            ,context.getString(R.string.pressure)))
        detailList.add(CurrentDetailData(R.drawable.humidity,"${dailyData.humidity} %"
            ,context.getString(R.string.humidity)))
        detailList.add(CurrentDetailData(R.drawable.ic_dew,"${getIntegerNumber(dailyData.dew_point)}°C"
            ,context.getString(R.string.dew_point)))
        detailList.add(CurrentDetailData(R.drawable.uv_index, dailyData.uvi.toString()
            ,context.getString(R.string.uv_index)))
        detailList.add(CurrentDetailData(R.drawable.cloudy,"${dailyData.clouds} %"
            ,context.getString(R.string.cloudiness)))
        detailList.add(CurrentDetailData(R.drawable.wi_dust_wind,"${dailyData.wind_speed} m"
            ,context.getString(R.string.wind_speed)))
        detailList.add(CurrentDetailData(R.drawable.wi_windsock,"${dailyData.wind_gust} m"
            ,context.getString(R.string.wind_gust)))
        detailList.add(CurrentDetailData(R.drawable.sunrise,getTimeFormat(dailyData.sunrise)
            ,context.getString(R.string.sunrise)))
        detailList.add(CurrentDetailData(R.drawable.sunset,getTimeFormat(dailyData.sunset)
            ,context.getString(R.string.sunset)))
    }

    fun getIntegerNumber(num:Double):String{
        return DecimalFormat("#").format(num)
    }

    fun getTimeFormat(unixTime:Int):String{
        return SimpleDateFormat("HH:mm").format(Date(unixTime*1000L))
    }
}