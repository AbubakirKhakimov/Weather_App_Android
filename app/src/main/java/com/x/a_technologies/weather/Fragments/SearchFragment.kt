package com.x.a_technologies.weather.Fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import com.x.a_technologies.weather.API
import com.x.a_technologies.weather.Adapters.SearchCallBack
import com.x.a_technologies.weather.Adapters.SearchResultsAdapter
import com.x.a_technologies.weather.Datas.DatasCityLocationAPI.CityLocation
import com.x.a_technologies.weather.Datas.SettingsData
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.FragmentSearchBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment(),SearchCallBack {

    lateinit var binding: FragmentSearchBinding
    lateinit var api:API
    var clear = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instalizationURL()

        binding.enterCityName.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.enterCityName, InputMethodManager.SHOW_IMPLICIT)

        binding.enterCityName.addTextChangedListener {
            if (it.toString() == ""){
                clear = true
                binding.rvSearchResults.adapter = SearchResultsAdapter(ArrayList(),this)
            }else {
                clear = false
                getResults(it.toString())
            }
        }

        binding.backButton.setOnClickListener {
            replaceFragment(EditLocationFragment())
        }

    }

    fun replaceFragment(fragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentConteiner,fragment).commit()
    }

    fun instalizationURL(){
        val retrofit = Retrofit.Builder()
            .baseUrl(SettingsData.BASE_URL_CITY_LOCATION)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(API::class.java)
    }

    fun getResults(cityName:String){
        binding.progressBar.visibility = View.VISIBLE

        api.getCityLocation(
            SettingsData.HOST,
            SettingsData.API_KEY_CITY_LOCATION,
            SettingsData.limit,
            SettingsData.type,
            cityName).enqueue(object :Callback<List<CityLocation>>{
            override fun onResponse(
                call: Call<List<CityLocation>>,
                response: Response<List<CityLocation>>
            ) {
                if (clear){
                    binding.rvSearchResults.adapter = SearchResultsAdapter(ArrayList(),this@SearchFragment)
                }else {
                    binding.rvSearchResults.adapter = SearchResultsAdapter(response.body()!!,this@SearchFragment)
                }

                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<CityLocation>>, t: Throwable) {

            }

        })
    }

    override fun setFragment() {
        write()
        replaceFragment(EditLocationFragment())
    }

    fun write(){
        val gson = Gson()
        val infoJson = gson.toJson(SettingsData.citiesList)

        val pref = requireActivity().getSharedPreferences(SettingsData.settingsFileName,MODE_PRIVATE)
        val edit = pref.edit()
        edit.putString(SettingsData.citiesDataKey,infoJson)
        edit.apply()
    }
}