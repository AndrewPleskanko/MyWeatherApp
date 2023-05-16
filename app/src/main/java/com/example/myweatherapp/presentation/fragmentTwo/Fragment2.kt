package com.example.myweatherapp.presentation.fragmentTwo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myweatherapp.data.image.ImageLoader
import com.example.myweatherapp.data.models.RetroResponse
import com.example.myweatherapp.databinding.FragmentTwoBinding
import com.example.myweatherapp.presentation.viewModels.FragmentCommonViewModel
import com.example.myweatherapp.domain.models.WeatherResponse
import com.example.myweatherapp.data.network.RetroService
import com.example.myweatherapp.domain.WeatherUseCase
import com.example.myweatherapp.presentation.viewModels.FragmentCommonViewModelFactory
import com.example.myweatherapp.data.repository.WeatherRepository
import com.example.myweatherapp.domain.ImageUseCase
import kotlinx.coroutines.launch
import java.util.*

class Fragment2 : Fragment() {
    private lateinit var binding: FragmentTwoBinding
    private val weatherRepository: WeatherRepository =
        WeatherRepository(RetroService.retrofitService)
    private val weatherUseCase: WeatherUseCase = WeatherUseCase(weatherRepository)
    private lateinit var imageLoader: ImageLoader
    private lateinit var imageUseCase: ImageUseCase

    private val model: FragmentCommonViewModel by viewModels {
        FragmentCommonViewModelFactory(
            weatherUseCase,
            imageUseCase
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTwoBinding.inflate(inflater, container, false)
        imageLoader = ImageLoader(requireContext())
        imageUseCase = ImageUseCase(imageLoader)

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
        model.weatherResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is RetroResponse.Failure -> {
                    handleFailureResponse(response)
                }
                RetroResponse.Loading -> {
                    showLoadingState()
                }
                is RetroResponse.Success -> {
                    handleSuccessResponse(response.value)
                }
            }
        }
    }
    private fun handleFailureResponse(response: RetroResponse.Failure) {
        binding.progressBar.visibility = View.GONE
        binding.txtError.visibility = View.VISIBLE
        binding.txtError.text = response.message
        binding.layoutInfo.visibility = View.GONE
    }

    private fun showLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.txtError.visibility = View.GONE
        binding.layoutInfo.visibility = View.GONE
    }

    private fun handleSuccessResponse(value: WeatherResponse) {
        binding.progressBar.visibility = View.GONE
        binding.txtError.visibility = View.GONE
        binding.layoutInfo.visibility = View.VISIBLE

        bindData(value)
    }
    @SuppressLint("SetTextI18n")
    private fun bindData(value: WeatherResponse) {
        binding.txtTemp.text = model.formatTemperature(value.main?.temp)
        binding.txtMaxTemp.text = model.formatTemperature(value.main?.temp_max)
        binding.txtMinTemp.text = model.formatTemperature(value.main?.temp_min)
        binding.txtFeelsLike.text = model.formatTemperature(value.main?.feels_like)


        binding.txtClouds.text = value.clouds?.all?.toString() ?: "N/A"
        binding.txtClouds.append(" %")

        binding.txtPressure.text = value.main?.pressure?.toString() ?: "N/A"
        binding.txtPressure.append(" hPa")

        binding.txtHumidity.text = value.main?.humidity?.toString() ?: "N/A"
        binding.txtHumidity.append(" %")

        val sunriseTimestamp = value.sys?.sunrise ?: 0L
        binding.txtSunrise.text = model.convertTimestampToHour(sunriseTimestamp)

        val sunsetTimestamp = value.sys?.sunset ?: 0L
        binding.txtSunset.text = model.convertTimestampToHour(sunsetTimestamp)

        val weather = value.weather?.get(0)
        if (weather != null) {
            val iconCode = weather.icon
            val iconUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"
            lifecycleScope.launch {
                model.loadImage(iconUrl, binding.weatherIconImageView)
            }
        }
    }
}