package com.example.myweatherapp

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweatherapp.core.RetroResponse
import com.example.myweatherapp.core.services.RetroService
import com.example.myweatherapp.weather.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {

    val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private val _weatherResponse: MutableLiveData<RetroResponse<WeatherResponse>> =
        MutableLiveData()

    val weatherResponse: LiveData<RetroResponse<WeatherResponse>>
        get() = _weatherResponse

    fun getWeatherData(cityName: String) {
        _weatherResponse.value = RetroResponse.Loading
        RetroService.retrofitService.getWeatherData(cityName,"0a0a5a560ad0e7583a777e2c5ce69ca5")
            .enqueue(object : Callback<WeatherResponse>{
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if(response.isSuccessful && response.body() != null){
                        _weatherResponse.value = RetroResponse.Success(value = response.body()!!)
                    }else{
                        Log.e(TAG, "onResponse: ${response.message()}")
                        _weatherResponse.value = RetroResponse.Failure(message = "No information found. Check city name", throwable = Exception(response.errorBody()?.string()))
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ", t)
                    _weatherResponse.value = RetroResponse.Failure(message = "No information found. Check internet connection", throwable = t)
                }
            })
    }

}