package com.x.a_technologies.weather.fragments

import android.view.*
import androidx.fragment.app.Fragment
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.adapters.editLocationFragmentAdapters.AddedLocationsAdapter
import com.x.a_technologies.weather.databinding.FragmentEditLocationBinding
import android.os.Bundle
import androidx.activity.OnBackPressedCallback

class EditLocationFragment : Fragment() {

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

        binding.rvAddedLocations.adapter = AddedLocationsAdapter()

        binding.enterCityName.setOnClickListener {
            replaceFragment(SearchFragment())
        }

        binding.backButton.setOnClickListener {
            replaceFragment(MainFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                replaceFragment(MainFragment())
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    fun replaceFragment(fragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentConteiner,fragment).commit()
    }

}