package com.example.myweatherapp.presentation.fragments.second

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myweatherapp.databinding.FragmentTwoBinding
import com.example.myweatherapp.presentation.viewModels.FragmentCommonViewModel
import com.example.myweatherapp.domain.models.WeatherResponse
import com.example.myweatherapp.presentation.fragments.BaseFragment

import kotlinx.coroutines.launch
import java.util.*

class FragmentDetail : BaseFragment() {

    private lateinit var binding: FragmentTwoBinding
    override val viewModel: FragmentCommonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers(binding.progressBar, binding.txtError, binding.layoutInfo)

        val cityName = arguments?.getString("cityName")
        binding.txtCity.text = cityName
        viewModel.getWeatherData(binding.txtCity.text.toString())
    }

    @SuppressLint("SetTextI18n")
    override fun bindData(weatherResponse: WeatherResponse) {
        binding.txtTemp.text = viewModel.formatTemperature(weatherResponse.main?.temp)
        binding.txtMaxTemp.text = viewModel.formatTemperature(weatherResponse.main?.temp_max)
        binding.txtMinTemp.text = viewModel.formatTemperature(weatherResponse.main?.temp_min)
        binding.txtFeelsLike.text = viewModel.formatTemperature(weatherResponse.main?.feels_like)


        binding.txtClouds.text = weatherResponse.clouds?.all?.toString() ?: "N/A"
        binding.txtClouds.append(" %")

        binding.txtPressure.text = weatherResponse.main?.pressure?.toString() ?: "N/A"
        binding.txtPressure.append(" hPa")

        binding.txtHumidity.text = weatherResponse.main?.humidity?.toString() ?: "N/A"
        binding.txtHumidity.append(" %")

        val sunriseTimestamp = weatherResponse.sys?.sunrise ?: 0L
        binding.txtSunrise.text = viewModel.convertTimestampToHour(sunriseTimestamp)

        val sunsetTimestamp = weatherResponse.sys?.sunset ?: 0L
        binding.txtSunset.text = viewModel.convertTimestampToHour(sunsetTimestamp)

        val weather = weatherResponse.weather?.get(0)
        if (weather != null) {
            val iconCode = weather.icon
            val iconUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"
            lifecycleScope.launch {
                viewModel.loadImage(iconUrl, binding.weatherIconImageView)
            }
        }
    }
}