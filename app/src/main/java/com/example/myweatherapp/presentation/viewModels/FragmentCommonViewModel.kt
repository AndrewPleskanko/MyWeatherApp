package com.example.myweatherapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.data.models.RetroResponse
import com.example.myweatherapp.domain.models.WeatherResponse
import com.example.myweatherapp.domain.WeatherUseCase
import kotlinx.coroutines.launch

class FragmentCommonViewModel(private val weatherUseCase: WeatherUseCase) : ViewModel() {
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
    private val _iconUrl = MutableLiveData<String?>()
    val iconUrl: LiveData<String?> = _iconUrl

}