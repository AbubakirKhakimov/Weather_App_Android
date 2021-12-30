package com.x.a_technologies.weather.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.hawk.Hawk
import com.x.a_technologies.weather.R
import com.x.a_technologies.weather.activities.IntroActivity
import com.x.a_technologies.weather.activities.MainActivity
import com.x.a_technologies.weather.databinding.FragmentSetLanguageBinding

class SetLanguageFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentSetLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetLanguageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.english.setOnClickListener{
            setLanguage("en")
        }

        binding.russian.setOnClickListener {
            setLanguage("ru")
        }

    }

    fun setLanguage(lang:String){
        Hawk.put("pref_lang", lang)
        startActivity(Intent(requireActivity(), IntroActivity::class.java))
        requireActivity().finish()
    }
}