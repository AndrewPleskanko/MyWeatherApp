package com.example.myweatherapp.weather

data class Main (
    var temp: Double? = null,

    var pressure: Double? = null,

    var humidity: Int? = null,

    var tempMin: Double? = null,

    var tempMax: Double? = null,

    var seaLevel: Double? = null,

    var grndLevel: Double? = null
)