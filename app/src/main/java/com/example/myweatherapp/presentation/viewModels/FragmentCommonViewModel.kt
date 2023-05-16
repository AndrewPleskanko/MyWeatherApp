package com.example.myweatherapp.presentation.viewModels

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.data.models.RetroResponse
import com.example.myweatherapp.domain.models.WeatherResponse
import com.example.myweatherapp.domain.WeatherUseCase
import kotlinx.coroutines.launch

class FragmentCommonViewModel(private val weatherUseCase: WeatherUseCase, private val imageLoader: com.example.myweatherapp.domain.ImageUseCase) : ViewModel() {
    private val _weatherResponse: MutableLiveData<RetroResponse<WeatherResponse>> = MutableLiveData()
    val weatherResponse: LiveData<RetroResponse<WeatherResponse>> get() = _weatherResponse

    fun getWeatherData(cityName: String) {
        viewModelScope.launch {
            _weatherResponse.value = RetroResponse.Loading
            _weatherResponse.value = weatherUseCase.getWeatherData(cityName)
        }
    }
    fun formatTemperature(temperature: Double?): String {
        return weatherUseCase.formatTemperature(temperature)
    }

    fun convertTimestampToHour(timestamp: Long): String {
        return weatherUseCase.convertTimestampToHour(timestamp)
    }
    suspend fun loadImage(url: String, imageView: ImageView) {
        imageLoader.loadImage(url, imageView)
    }

}