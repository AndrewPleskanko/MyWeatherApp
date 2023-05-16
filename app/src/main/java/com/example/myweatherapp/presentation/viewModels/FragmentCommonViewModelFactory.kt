package com.example.myweatherapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.data.image.ImageLoader
import com.example.myweatherapp.domain.ImageUseCase
import com.example.myweatherapp.domain.WeatherUseCase

@Suppress("UNCHECKED_CAST")
class FragmentCommonViewModelFactory(
    private val weatherUseCase: WeatherUseCase,
    private val imageUseCase: ImageUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FragmentCommonViewModel::class.java)) {
            return FragmentCommonViewModel(weatherUseCase, imageUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}