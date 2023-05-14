package com.example.myweatherapp.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.data.RetroResponse
import com.example.myweatherapp.data.services.RetroService
import com.example.myweatherapp.data.models.WeatherResponse
import com.example.myweatherapp.domain.WeatherUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentCommonViewModel(private val weatherUseCase: WeatherUseCase) : ViewModel() {
    private val _weatherResponse: MutableLiveData<RetroResponse<WeatherResponse>> = MutableLiveData()
    val weatherResponse: LiveData<RetroResponse<WeatherResponse>> get() = _weatherResponse

    fun getWeatherData(cityName: String) {
        viewModelScope.launch {
            _weatherResponse.value = RetroResponse.Loading
            _weatherResponse.value = weatherUseCase.getWeatherData(cityName)
        }
    }
}