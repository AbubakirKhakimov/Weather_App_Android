package com.x.a_technologies.weather.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import com.x.a_technologies.weather.api.API
import com.x.a_technologies.weather.datas.DatasCityLocationAPI.CityLocation
import com.x.a_technologies.weather.datas.PublicDatas
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.adapters.searchFragmentAdapters.SearchCallBack
import com.x.a_technologies.weather.adapters.searchFragmentAdapters.SearchResultsAdapter
import com.x.a_technologies.weather.databinding.FragmentSearchBinding
import com.x.a_technologies.weather.datas.Constants
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment(), SearchCallBack {

    lateinit var binding: FragmentSearchBinding
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

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                replaceFragment(EditLocationFragment())
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)

    }

    fun getResults(cityName:String){
        binding.progressBar.visibility = View.VISIBLE

        PublicDatas.getCityLocationApi().getCityLocation(
            Constants.HOST,
            Constants.API_KEY_CITY_LOCATION,
            Constants.limit,
            Constants.type,
            cityName).enqueue(object :Callback<List<CityLocation>>{
            override fun onResponse(
                call: Call<List<CityLocation>>,
                response: Response<List<CityLocation>>
            ) {
                if (response.isSuccessful){
                    if (clear){
                        binding.rvSearchResults.adapter =
                            SearchResultsAdapter(ArrayList(),this@SearchFragment)
                    }else {
                        binding.rvSearchResults.adapter =
                            SearchResultsAdapter(response.body()!!,this@SearchFragment)
                    }
                }else{
                    Toast.makeText(context, "No internet connection!", Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<CityLocation>>, t: Throwable) {
                Toast.makeText(context, "No internet connection!", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }

        })
    }

    fun replaceFragment(fragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentConteiner,fragment).commit()
    }

    override fun setFragment() {
        PublicDatas.writeCityList()
        if (PublicDatas.citiesList.size == 1){
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentConteinerIntro,IntroFragment()).commit()
        }else {
            replaceFragment(EditLocationFragment())
        }
    }

}