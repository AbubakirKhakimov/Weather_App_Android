package com.x.a_technologies.weather.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.Datas.CitiesData
import com.x.a_technologies.weather.Datas.DatasCityLocationAPI.CityLocation
import com.x.a_technologies.weather.Datas.SettingsData
import com.x.a_technologies.weather.databinding.SearchResultsItemLayoutBinding

interface SearchCallBack{
    fun setFragment()
}

class SearchResultsAdapter(val cityLocationList: List<CityLocation>, val callBack:SearchCallBack):RecyclerView.Adapter<SearchResultsAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: SearchResultsItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(SearchResultsItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = cityLocationList[position]
        holder.binding.cityName.text = item.name
        holder.binding.cityAndCountryName.text = "${item.adminDivision1.name}/${item.country.name}"

        holder.binding.resultItem.setOnClickListener {
            SettingsData.citiesList.add(CitiesData(
                item.name,
                item.adminDivision1.name,
                item.country.name,
                item.coordinates.latitude,
                item.coordinates.longitude))

            callBack.setFragment()
        }
    }

    override fun getItemCount(): Int {
        return cityLocationList.size
    }
}