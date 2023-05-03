package com.example.myweatherapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.example.myweatherapp.core.RetroResponse
import com.example.myweatherapp.databinding.ActivityMainBinding
import com.example.myweatherapp.weather.WeatherResponse


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val model : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers()
        binding.button.setOnClickListener {
            if(binding.txtCityName.text.isNullOrEmpty()){
                binding.txtCityName.error = "Please enter city name"
                return@setOnClickListener
            }
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            model.getWeatherData(binding.txtCityName.text.toString())
        }
    }

    private fun observers() {
        model.weatherResponse.observe(this){
            when(it){
                is RetroResponse.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtError.visibility = View.VISIBLE
                    binding.txtError.text = it.message
                    binding.layoutInfo.visibility = View.GONE
                }
                RetroResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.txtError.visibility = View.GONE
                    binding.layoutInfo.visibility = View.GONE
                }
                is RetroResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtError.visibility = View.GONE
                    binding.layoutInfo.visibility = View.VISIBLE

                    bindData(it.value)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(value: WeatherResponse) {
        binding.txtCity.text =
            (binding.txtCityName.text.toString() + ", " + (value.sys?.country ?: "N/A"))
        binding.txtTemp.text = value.main?.temp?.let {
            it.toString() + "kelvin"
        } ?: "N/A"
        binding.txtClouds.text = value.clouds?.all?.toString() ?: "N/A"
        binding.txtPressure.text = value.main?.pressure?.toString() ?: "N/A"
    }
}