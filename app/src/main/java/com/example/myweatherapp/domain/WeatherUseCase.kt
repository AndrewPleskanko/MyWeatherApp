package com.example.myweatherapp.domain

import com.example.myweatherapp.data.RetroResponse
import com.example.myweatherapp.data.models.WeatherResponse
import com.example.myweatherapp.viewModels.WeatherRepository

class WeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun getWeatherData(cityName: String): RetroResponse<WeatherResponse> {
        return try {
            val response = weatherRepository.getWeatherData(cityName)
            RetroResponse.Success(response)
        } catch (e: Exception) {
            RetroResponse.Failure("No information found. Check internet connection", e)
        }
    }
}