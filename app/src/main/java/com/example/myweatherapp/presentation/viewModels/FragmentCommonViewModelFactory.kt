package com.example.myweatherapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.domain.WeatherUseCase

@Suppress("UNCHECKED_CAST")
class FragmentCommonViewModelFactory(private val weatherUseCase: WeatherUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FragmentCommonViewModel::class.java)) {
            return FragmentCommonViewModel(weatherUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}