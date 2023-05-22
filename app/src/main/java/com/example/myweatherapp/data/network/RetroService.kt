package com.example.myweatherapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
//отримати доступ до інтерфейсу для виконання HTTP-запитів
object RetroService {

    val retrofitService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }
}