package com.x.a_technologies.weather.adapters.editLocationFragmentAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.x.a_technologies.weather.datas.SettingsData
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.AddedLocationItemLayoutBinding

interface LocationCallBack{
    fun writeFile()
}

class AddedLocationsAdapter(val locationCallBack: LocationCallBack):RecyclerView.Adapter<AddedLocationsAdapter.ItemHolder>(),PopupMenu.OnMenuItemClickListener {
    inner class ItemHolder(val binding: AddedLocationItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    private lateinit var context:Context
    var popupPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
       return ItemHolder(AddedLocationItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = SettingsData.citiesList[position]
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
        return SettingsData.citiesList.size
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.makeMain -> {
                SettingsData.citiesList.add(0,SettingsData.citiesList[popupPosition])
                SettingsData.citiesList.removeAt(popupPosition+1)
            }
            R.id.delate -> {
                SettingsData.citiesList.removeAt(popupPosition)
            }
        }
        notifyDataSetChanged()
        locationCallBack.writeFile()
        return true
    }
}