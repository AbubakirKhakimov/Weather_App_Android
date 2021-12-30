package com.x.a_technologies.weather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.databinding.FragmentNotConnectionBinding

class NotConnectionFragment : Fragment() {

    lateinit var binding: FragmentNotConnectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotConnectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.againConnection.setOnClickListener {
            replaceFragment(IntroFragment())
        }

    }

    fun replaceFragment(fragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentConteinerIntro,fragment).commit()
    }

}