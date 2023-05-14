package com.example.myweatherapp.data.network

import com.example.myweatherapp.domain.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("data/2.5/weather?")
    suspend fun getWeatherData(
        @Query("q") cityName: String,
        @Query("APPID") appId: String
    ): WeatherResponse

}