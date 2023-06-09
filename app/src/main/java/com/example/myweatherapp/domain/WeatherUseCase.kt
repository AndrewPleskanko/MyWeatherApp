package com.example.myweatherapp.domain

import com.example.myweatherapp.data.network.RetroService
import com.example.myweatherapp.domain.models.WeatherResponse
import com.example.myweatherapp.data.repository.WeatherRepository
import com.example.myweatherapp.presentation.models.RetroResponse
import java.text.SimpleDateFormat
import java.util.*

class WeatherUseCase {

    private val weatherRepository = WeatherRepository(RetroService.retrofitService)

    suspend fun getWeatherData(cityName: String): RetroResponse<WeatherResponse> {
        return try {
            val response = weatherRepository.getWeatherData(cityName)
           RetroResponse.Success(response)
        } catch (e: Exception) {
           RetroResponse.Failure(
                "No information found. Check internet connection",
                e
            )
        }
    }

    fun formatTemperature(temperature: Double?): String {
        return temperature?.let {
            val celsiusTemp = it - 273.15
            "${celsiusTemp.toInt()}℃"
        } ?: "N/A"
    }

    fun convertTimestampToHour(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = Date(timestamp * 1000)
        val formattedTime = dateFormat.format(date)
        return "$formattedTime hours"
    }

}