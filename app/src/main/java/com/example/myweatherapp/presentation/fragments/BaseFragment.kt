package com.example.myweatherapp.presentation.fragments

import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myweatherapp.domain.models.WeatherResponse
import com.example.myweatherapp.presentation.viewModels.FragmentCommonViewModel

abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: FragmentCommonViewModel

    protected abstract fun bindData(weatherResponse: WeatherResponse)

    protected fun observers(progressBar: ProgressBar, txtError: TextView, layoutInfo: LinearLayout) {
        viewModel.weatherResponse.observe(viewLifecycleOwner) {
            it.display(progressBar, txtError, layoutInfo)
            it.getWeatherResponse()?.let { response -> bindData(response) }
        }
    }

}