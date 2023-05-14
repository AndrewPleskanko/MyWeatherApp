package com.example.myweatherapp.presentation.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myweatherapp.R
import com.example.myweatherapp.data.network.RetroResponse
import com.example.myweatherapp.databinding.FragmentOneBinding
import com.example.myweatherapp.presentation.viewModels.FragmentCommonViewModel
import com.example.myweatherapp.domain.models.WeatherResponse
import com.example.myweatherapp.data.services.RetroService.retrofitService
import com.example.myweatherapp.domain.WeatherUseCase
import com.example.myweatherapp.presentation.viewModels.FragmentCommonViewModelFactory
import com.example.myweatherapp.domain.repository.WeatherRepository

class Fragment1 : Fragment() {
    private lateinit var binding: FragmentOneBinding
    private val weatherRepository: WeatherRepository = WeatherRepository(retrofitService)
    private val weatherUseCase: WeatherUseCase = WeatherUseCase(weatherRepository)

    private val model: FragmentCommonViewModel by viewModels { FragmentCommonViewModelFactory(weatherUseCase) }

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
            val cityName = binding.txtCityName.text.toString()
            val bundle = Bundle().apply {
                putString("cityName", cityName)
            }
            findNavController().navigate(R.id.action_fragment1_to_fragment2, bundle)
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
        binding.txtCity.text = (binding.txtCityName.text.toString() + ", " + (value.sys?.country ?: "N/A"))
        val celsiusTemp = model.formatTemperature(value.main?.temp)
        binding.txtTemp.text = celsiusTemp
        binding.txtClouds.text = value.clouds?.all?.toString() ?: "N/A"
        binding.txtPressure.text = value.main?.pressure?.toString() ?: "N/A"
    }
}