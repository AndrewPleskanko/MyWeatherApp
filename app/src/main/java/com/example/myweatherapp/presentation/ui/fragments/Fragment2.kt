package com.example.myweatherapp.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myweatherapp.data.image.ImageLoader
import com.example.myweatherapp.data.network.RetroResponse
import com.example.myweatherapp.databinding.FragmentTwoBinding
import com.example.myweatherapp.presentation.viewModels.FragmentCommonViewModel
import com.example.myweatherapp.domain.models.WeatherResponse
import com.example.myweatherapp.data.services.RetroService
import com.example.myweatherapp.domain.WeatherUseCase
import com.example.myweatherapp.presentation.viewModels.FragmentCommonViewModelFactory
import com.example.myweatherapp.domain.repository.WeatherRepository
import java.util.*

class Fragment2 : Fragment() {
    private lateinit var binding: FragmentTwoBinding
    private val weatherRepository: WeatherRepository = WeatherRepository(RetroService.retrofitService)
    private val weatherUseCase: WeatherUseCase = WeatherUseCase(weatherRepository)
    private val model: FragmentCommonViewModel by viewModels { FragmentCommonViewModelFactory(weatherUseCase) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTwoBinding.inflate(inflater, container, false)
        observers()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityName = arguments?.getString("cityName")
        binding.txtCity.text = cityName
        model.getWeatherData(binding.txtCity.text.toString())
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
        binding.txtTemp.text = model.formatTemperature(value.main?.temp)
        binding.txtMaxTemp.text = model.formatTemperature(value.main?.temp_max)
        binding.txtMinTemp.text = model.formatTemperature(value.main?.temp_min)
        binding.txtFeelsLike.text = model.formatTemperature(value.main?.feels_like)

        binding.txtClouds.text = value.clouds?.all?.toString() ?: "N/A"
        binding.txtPressure.text = value.main?.pressure?.toString() ?: "N/A"
        binding.txtHumidity.text = value.main?.humidity?.toString() ?: "N/A"

        val sunriseTimestamp = value.sys?.sunrise ?: 0L
        binding.txtSunrise.text = model.convertTimestampToHour(sunriseTimestamp)

        val sunsetTimestamp = value.sys?.sunset ?: 0L
        binding.txtSunset.text = model.convertTimestampToHour(sunsetTimestamp)

        val weather = value.weather?.get(0)


        if (weather != null) {
            val iconCode = weather.icon

            // У вашому фрагменті
            val imageLoader = ImageLoader(requireContext())

// Завантаження зображення
            val iconUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"
            imageLoader.loadImage(iconUrl, binding.weatherIconImageView)

        }
    }
}