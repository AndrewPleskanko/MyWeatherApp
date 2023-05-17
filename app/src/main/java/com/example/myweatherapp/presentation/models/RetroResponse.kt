package com.example.myweatherapp.presentation.models

import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.myweatherapp.domain.models.WeatherResponse

sealed class RetroResponse<out T> {

    abstract fun display(progressBar: ProgressBar, txtError: TextView, layoutInfo: LinearLayout)

    open fun getWeatherResponse(): WeatherResponse? = null

    data class Success(private val value: WeatherResponse) : RetroResponse<WeatherResponse>() {
        override fun display(progressBar: ProgressBar, txtError: TextView, layoutInfo: LinearLayout) {
            progressBar.visibility = View.GONE
            txtError.visibility = View.GONE
            layoutInfo.visibility = View.VISIBLE
        }

        override fun getWeatherResponse(): WeatherResponse {
            return value
        }
    }

    data class Failure(
        private val message: String,
        private val throwable: Throwable?
    ) : RetroResponse<Nothing>() {
        override fun display(progressBar: ProgressBar, txtError: TextView, layoutInfo: LinearLayout) {
            progressBar.visibility = View.GONE
            txtError.visibility = View.VISIBLE
            txtError.text = message
            layoutInfo.visibility = View.GONE
        }
    }

    object Loading : RetroResponse<Nothing>() {
        override fun display(progressBar: ProgressBar, txtError: TextView, layoutInfo: LinearLayout) {
            progressBar.visibility = View.VISIBLE
            txtError.visibility = View.GONE
            layoutInfo.visibility = View.GONE
        }
    }
}