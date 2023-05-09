package com.example.myweatherapp.data.services

import com.example.myweatherapp.data.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIs {

    @GET("data/2.5/weather?")
    fun getWeatherData(@Query("q") cityName : String, @Query("APPID") appId : String) : Call<WeatherResponse>

}