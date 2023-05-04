package com.example.myweatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.myweatherapp.R
import com.google.android.material.button.MaterialButton

class Fragment2 : Fragment() {

    private lateinit var buttonNavFragmentTwo: MaterialButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_two, container, false)
        buttonNavFragmentTwo = view.findViewById(R.id.buttonNavFragmentTwo)
        buttonNavFragmentTwo.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_fragment2_to_fragment1)
        }
        return view;
    }

}