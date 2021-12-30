package com.x.a_technologies.weather.adapters.dailyShowDetailFragmentAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.databinding.DailyShowDetailPagerLayoutBinding
import com.x.a_technologies.weather.datas.DatasWeatherAPI.Daily
import java.text.DecimalFormat

class DailyShowDetailPagerAdapter(val dailyDataList: List<Daily>, val context: Context):RecyclerView.Adapter<DailyShowDetailPagerAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: DailyShowDetailPagerLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(DailyShowDetailPagerLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = dailyDataList[position]
        val iconId = item.weather[0].icon.substring(0, 2)
        holder.binding.dayAnimationView.setAnimation("${iconId}d.json")
        holder.binding.nightAnimationView.setAnimation("${iconId}n.json")
        holder.binding.dayTemp.text = getIntegerNumber(item.temp.day)
        holder.binding.nightTemp.text = getIntegerNumber(item.temp.night)
        holder.binding.feelsLikeDay.text = getIntegerNumber(item.feels_like.day)
        holder.binding.feelsLikeNight.text = getIntegerNumber(item.feels_like.night)
        holder.binding.description.text = item.weather[0].description
        holder.binding.RVDetails.adapter = DailyShowDetailAdapter(item, context)
    }

    override fun getItemCount(): Int {
       return dailyDataList.size
    }

    fun getIntegerNumber(num:Double):String{
        return DecimalFormat("#").format(num)
    }
}