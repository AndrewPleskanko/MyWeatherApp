package com.example.myweatherapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myweatherapp.R
import com.example.myweatherapp.core.RetroResponse
import com.example.myweatherapp.databinding.FragmentOneBinding
import com.example.myweatherapp.viewModels.FragmentOneViewModel
import com.example.myweatherapp.weather.WeatherResponse
import com.google.android.material.button.MaterialButton

class Fragment1 : Fragment() {
    private lateinit var binding: FragmentOneBinding
    private val model: FragmentOneViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOneBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {
            if (binding.txtCityName.text.isNullOrEmpty()) {
                binding.txtCityName.error = "Please enter city name"
                return@setOnClickListener
            }
            activity?.currentFocus?.let { view ->
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            model.getWeatherData(binding.txtCityName.text.toString())
        }

        binding.buttonNavFragmentOne.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_fragment1_to_fragment2)
        }

        observers()
        return binding.root
    }

    private fun observers() {
        model.weatherResponse.observe(viewLifecycleOwner) {
            when (it) {
                is RetroResponse.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtError.visibility = View.VISIBLE
                    binding.txtError.text = it.message
                    binding.layoutInfo.visibility = View.GONE
                }
                RetroResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.txtError.visibility = View.GONE
                    binding.layoutInfo.visibility = View.GONE
                }
                is RetroResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtError.visibility = View.GONE
                    binding.layoutInfo.visibility = View.VISIBLE

                    bindData(it.value)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(value: WeatherResponse) {
        binding.txtCity.text =
            ( binding.txtCityName.text.toString() + ", " + (value.sys?.country ?: "N/A"))
        binding.txtTemp.text = value.main?.temp?.let {
            it.toString() + "kelvin"
        } ?: "N/A"
        binding.txtClouds.text = value.clouds?.all?.toString() ?: "N/A"
        binding.txtPressure.text = value.main?.pressure?.toString() ?: "N/A"
    }
}