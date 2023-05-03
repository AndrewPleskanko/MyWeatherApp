package com.example.myweatherapp.core.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
object RetroService {

    val retrofitService: APIs by lazy {
        retrofit.create(APIs::class.java)
    }
}