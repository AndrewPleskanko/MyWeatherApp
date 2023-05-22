package com.example.myweatherapp.domain.models

data class Sys(
    var message: Double? = null,

    var country: String? = null,

    var sunrise: Long? = null,

    var sunset: Long? = null
)
