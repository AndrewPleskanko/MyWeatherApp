package com.example.myweatherapp.data.repository

import com.example.myweatherapp.domain.models.WeatherResponse
import com.example.myweatherapp.data.network.RetrofitService


class WeatherRepository(private val retrofitService: RetrofitService) {
    suspend fun getWeatherData(cityName: String): WeatherResponse {
        return retrofitService.getWeatherData(cityName, "0a0a5a560ad0e7583a777e2c5ce69ca5")
    }
}