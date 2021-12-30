package com.x.a_technologies.weather.adapters.editLocationFragmentAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.datas.PublicDatas
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.AddedLocationItemLayoutBinding

class AddedLocationsAdapter():RecyclerView.Adapter<AddedLocationsAdapter.ItemHolder>(),PopupMenu.OnMenuItemClickListener {
    inner class ItemHolder(val binding: AddedLocationItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    private lateinit var context:Context
    var popupPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
       return ItemHolder(AddedLocationItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = PublicDatas.citiesList[position]
        holder.binding.cityName.text = item.cityName
        holder.binding.cityAndCountryName.text = "${item.regionName}/${item.countryName}"

        holder.binding.moreFuns.setOnClickListener {
            popupPosition = position
            val popup = PopupMenu(context,it)
            popup.setOnMenuItemClickListener(this)
            popup.inflate(R.menu.menu)
            popup.show()
        }
    }

    override fun getItemCount(): Int {
        return PublicDatas.citiesList.size
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.makeMain -> {
                PublicDatas.citiesList.add(0,PublicDatas.citiesList[popupPosition])
                PublicDatas.citiesList.removeAt(popupPosition+1)
                PublicDatas.weatherData.add(0,PublicDatas.weatherData[popupPosition])
                PublicDatas.weatherData.removeAt(popupPosition+1)

                notifyDataSetChanged()
                PublicDatas.writeCityList()
            }
            R.id.delate -> {
                if (PublicDatas.citiesList.size == 1){
                    Toast.makeText(context, "Leave at least one place!", Toast.LENGTH_SHORT).show()
                }else{
                    PublicDatas.citiesList.removeAt(popupPosition)
                    PublicDatas.weatherData.removeAt(popupPosition)

                    notifyDataSetChanged()
                    PublicDatas.writeCityList()
                }
            }
        }
        return true
    }
}