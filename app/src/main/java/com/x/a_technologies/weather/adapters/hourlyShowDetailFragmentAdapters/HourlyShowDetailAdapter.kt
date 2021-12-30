package com.x.a_technologies.weather.adapters.hourlyShowDetailFragmentAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.HourlyDailyShowDetailItemLayoutBinding
import com.x.a_technologies.weather.datas.CurrentDetailData
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Hourly
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class HourlyShowDetailAdapter(val hourlyData:Hourly, val context: Context):RecyclerView.Adapter<HourlyShowDetailAdapter.ItemHolder>() {
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
        detailList.add(CurrentDetailData(R.drawable.temperature_feels_like,"${getIntegerNumber(hourlyData.feels_like)}°C"
            ,context.getString(R.string.feels_like)))
        detailList.add(CurrentDetailData(R.drawable.ic_pressure,"${hourlyData.pressure} hPa"
            ,context.getString(R.string.pressure)))
        detailList.add(CurrentDetailData(R.drawable.humidity,"${hourlyData.humidity} %"
            ,context.getString(R.string.humidity)))
        detailList.add(CurrentDetailData(R.drawable.ic_dew,"${getIntegerNumber(hourlyData.dew_point)}°C"
            ,context.getString(R.string.dew_point)))
        detailList.add(CurrentDetailData(R.drawable.uv_index, hourlyData.uvi.toString()
            ,context.getString(R.string.uv_index)))
        detailList.add(CurrentDetailData(R.drawable.cloudy,"${hourlyData.clouds} %"
            ,context.getString(R.string.cloudiness)))
        detailList.add(CurrentDetailData(R.drawable.ic_visibility,"${hourlyData.visibility} m"
            ,context.getString(R.string.visibility)))
        detailList.add(CurrentDetailData(R.drawable.wi_dust_wind,"${hourlyData.wind_speed} m"
            ,context.getString(R.string.wind_speed)))
        detailList.add(CurrentDetailData(R.drawable.wi_windsock,"${hourlyData.wind_gust} m"
            ,context.getString(R.string.wind_gust)))
    }

    fun getIntegerNumber(num:Double):String{
        return DecimalFormat("#").format(num)
    }
}