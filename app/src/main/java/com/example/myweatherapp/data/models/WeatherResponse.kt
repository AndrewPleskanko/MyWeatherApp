package com.example.myweatherapp.data.models

data class WeatherResponse(

    var base: String? = null,

    var main: Main? = null,

    val weather: List<Weather>? = null,

    var clouds: Clouds? = null,

    var dt: Int? = null,

    var sys: Sys? = null,

    var timezone: Int? = null,

    var id: Int? = null,

    var name: String? = null,

    var cod: Int? = null
)