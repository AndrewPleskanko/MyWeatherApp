package com.example.myweatherapp.presentation.viewModels

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.domain.models.WeatherResponse
import com.example.myweatherapp.domain.ImageUseCase
import com.example.myweatherapp.domain.WeatherUseCase
import com.example.myweatherapp.presentation.models.RetroResponse
import kotlinx.coroutines.launch

class FragmentCommonViewModel : ViewModel() {

    private val imageUseCase = ImageUseCase()
    private val weatherUseCase = WeatherUseCase()

    private val _weatherResponse: MutableLiveData<RetroResponse<WeatherResponse>> =
        MutableLiveData()
    val weatherResponse: LiveData<RetroResponse<WeatherResponse>> get() = _weatherResponse

    fun getWeatherData(cityName: String) {
        viewModelScope.launch {
            //показуємо індикатор завантаження
            _weatherResponse.postValue(RetroResponse.Loading)
            //передаємо як змінну до viewModel
            _weatherResponse.postValue(weatherUseCase.getWeatherData(cityName))

        }
    }

    fun formatTemperature(temperature: Double?): String {
        return weatherUseCase.formatTemperature(temperature)
    }

    fun convertTimestampToHour(timestamp: Long): String {
        return weatherUseCase.convertTimestampToHour(timestamp)
    }

    suspend fun loadImage(url: String, imageView: ImageView) {
        imageUseCase.loadImage(url, imageView)
    }

}