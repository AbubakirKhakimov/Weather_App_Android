package com.x.a_technologies.weather.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.x.a_technologies.weather.datas.SettingsData
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.adapters.editLocationFragmentAdapters.AddedLocationsAdapter
import com.x.a_technologies.weather.adapters.editLocationFragmentAdapters.LocationCallBack
import com.x.a_technologies.weather.databinding.FragmentEditLocationBinding

class EditLocationFragment : Fragment(), LocationCallBack {

    lateinit var binding: FragmentEditLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditLocationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAddedLocations.adapter = AddedLocationsAdapter(this)

        binding.enterCityName.setOnClickListener {
            replaceFragment(SearchFragment())
        }

        binding.backButton.setOnClickListener {
            replaceFragment(MainFragment())
        }
    }

    fun write(){
        val gson = Gson()
        val infoJson = gson.toJson(SettingsData.citiesList)

        val pref = requireActivity().getSharedPreferences(
            SettingsData.settingsFileName,
            Context.MODE_PRIVATE
        )
        val edit = pref.edit()
        edit.putString(SettingsData.citiesDataKey,infoJson)
        edit.apply()
    }

    fun replaceFragment(fragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentConteiner,fragment).commit()
    }

    override fun writeFile() {
        write()
    }

}