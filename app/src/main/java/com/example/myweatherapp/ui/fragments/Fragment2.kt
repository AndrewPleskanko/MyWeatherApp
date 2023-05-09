package com.example.myweatherapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.myweatherapp.data.RetroResponse
import com.example.myweatherapp.databinding.FragmentTwoBinding
import com.example.myweatherapp.viewModels.FragmentCommonViewModel
import com.example.myweatherapp.data.models.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*

class Fragment2 : Fragment() {
    private lateinit var binding: FragmentTwoBinding
    private val model: FragmentCommonViewModel by viewModels()

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
        binding.txtTemp.text = formatTemperature(value.main?.temp)
        binding.txtMaxTemp.text = formatTemperature(value.main?.temp_max)
        binding.txtMinTemp.text = formatTemperature(value.main?.temp_min)
        binding.txtFeelsLike.text = formatTemperature(value.main?.feels_like)

        binding.txtClouds.text = value.clouds?.all?.toString() ?: "N/A"
        binding.txtPressure.text = value.main?.pressure?.toString() ?: "N/A"
        binding.txtHumidity.text = value.main?.humidity?.toString() ?: "N/A"

        val sunriseTimestamp = value.sys?.sunrise ?: 0L
        binding.txtSunrise.text = convertTimestampToHour(sunriseTimestamp)

        val sunsetTimestamp = value.sys?.sunset ?: 0L
        binding.txtSunset.text = convertTimestampToHour(sunsetTimestamp)


        val weather = value.weather?.get(0)

// Check if the weather object is not null
        if (weather != null) {
            val iconCode = weather.icon

            // Set the icon code to the ImageView tag
            binding.weatherIconImageView.tag = iconCode

            // Construct the icon URL using the icon code
            val iconUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"

            // Load the image using Glide and display it in the ImageView
            Glide.with(requireContext())
                .load(iconUrl)
                .into(binding.weatherIconImageView)
        }
    }

    private fun formatTemperature(temperature: Double?): String {
        return temperature?.let {
            val celsiusTemp = it - 273.15
            "${celsiusTemp.toInt()}℃"
        } ?: "N/A"
    }

    private fun convertTimestampToHour(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = Date(timestamp * 1000)
        return dateFormat.format(date)
    }
}