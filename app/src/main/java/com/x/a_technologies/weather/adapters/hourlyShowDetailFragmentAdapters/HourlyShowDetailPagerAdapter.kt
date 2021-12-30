package com.x.a_technologies.weather.adapters.hourlyShowDetailFragmentAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.databinding.HourlyShowDetailPagerLayoutBinding
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Hourly
import java.text.DecimalFormat

class HourlyShowDetailPagerAdapter(val hourlyList: List<Hourly>, val context: Context):RecyclerView.Adapter<HourlyShowDetailPagerAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: HourlyShowDetailPagerLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(HourlyShowDetailPagerLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = hourlyList[position]
        holder.binding.animationView.setAnimation("${item.weather[0].icon}.json")
        holder.binding.currentTimeTemp.text = getIntegerNumber(item.temp)
        holder.binding.description.text = item.weather[0].description
        holder.binding.RVDetails.adapter = HourlyShowDetailAdapter(hourlyList[position], context)
    }

    override fun getItemCount(): Int {
        return 24
    }

    fun getIntegerNumber(num:Double):String{
        return DecimalFormat("#").format(num)
    }
}