package com.example.myweatherapp.data.models

data class Main(
    var temp: Double? = null,

    var pressure: Double? = null,

    var feels_like: Double? = null,

    var humidity: Int? = null,

    var temp_min: Double? = null,

    var temp_max: Double? = null,

    var seaLevel: Double? = null,

    var grndLevel: Double? = null
)