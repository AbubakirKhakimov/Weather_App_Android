package com.x.a_technologies.weather.adapters.hourlyShowDetailFragmentAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.HourlyShowDetailItemLayoutBinding
import com.x.a_technologies.weather.datas.CurrentDetailData
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Hourly
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class HourlyShowDetailAdapter(val hourlyData:Hourly):RecyclerView.Adapter<HourlyShowDetailAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: HourlyShowDetailItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    var detailList = ArrayList<CurrentDetailData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(HourlyShowDetailItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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
        detailList.add(CurrentDetailData(R.drawable.temperature_feels_like,"${getIntegerNumber(hourlyData.feels_like)}°C","Feels like"))
        detailList.add(CurrentDetailData(R.drawable.ic_pressure,"${hourlyData.pressure} hPa","Pressure"))
        detailList.add(CurrentDetailData(R.drawable.humidity,"${hourlyData.humidity} %","Humidity"))
        detailList.add(CurrentDetailData(R.drawable.ic_dew,"${getIntegerNumber(hourlyData.dew_point)}°C","Dew point"))
        detailList.add(CurrentDetailData(R.drawable.uv_index, hourlyData.uvi.toString(),"UV index"))
        detailList.add(CurrentDetailData(R.drawable.cloudy,"${hourlyData.clouds} %","Cloudiness"))
        detailList.add(CurrentDetailData(R.drawable.ic_visibility,"${hourlyData.visibility} m","Visibility"))
        detailList.add(CurrentDetailData(R.drawable.wi_dust_wind,"${hourlyData.wind_speed} m","Wind speed"))
        detailList.add(CurrentDetailData(R.drawable.wi_windsock,"${hourlyData.wind_gust} m","Wind gust"))
    }

    fun getIntegerNumber(num:Double):String{
        return DecimalFormat("#").format(num)
    }
}