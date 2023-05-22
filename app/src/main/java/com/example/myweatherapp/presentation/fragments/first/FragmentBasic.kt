package com.example.myweatherapp.presentation.fragments.first

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myweatherapp.R
import com.example.myweatherapp.databinding.FragmentOneBinding
import com.example.myweatherapp.domain.models.WeatherResponse
import com.example.myweatherapp.presentation.fragments.BaseFragment
import com.example.myweatherapp.presentation.viewModels.FragmentCommonViewModel

class FragmentBasic : BaseFragment() {

    private lateinit var binding: FragmentOneBinding
    override val viewModel: FragmentCommonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOneBinding.inflate(inflater, container, false)

        // обробник подій для отримання даних за назвою міста
        binding.button.setOnClickListener {
            if (binding.txtCityName.text.isNullOrEmpty()) {
                binding.txtCityName.error = getString(R.string.Enter_city)
                return@setOnClickListener
            }
            //перевірка активності на null
            activity?.currentFocus?.let { view ->
                //приховуємо клавіатуру після наданого запиту
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            viewModel.getWeatherData(binding.txtCityName.text.toString())
        }
        // кнопка напігації на наступний фрагмент
        binding.buttonNavFragmentOne.setOnClickListener {
            val cityName = binding.txtCityName.text.toString()
            val bundle = Bundle().apply {
                putString("cityName", cityName)
            }
            findNavController().navigate(R.id.action_fragment1_to_fragment2, bundle)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers(binding.progressBar, binding.txtError, binding.layoutInfo)
    }

    @SuppressLint("SetTextI18n")
    override fun bindData(weatherResponse: WeatherResponse) {
        binding.txtCity.text =
            (binding.txtCityName.text.toString() + ", " + (weatherResponse.sys?.country ?: "N/A"))
        binding.txtTemp.text = viewModel.formatTemperature(weatherResponse.main?.temp)
        binding.txtCity.text =
            (binding.txtCityName.text.toString() + ", " + (weatherResponse.sys?.country ?: "N/A"))

        binding.txtClouds.text = weatherResponse.clouds?.all?.toString() ?: "N/A"
        binding.txtClouds.append(" %")

        binding.txtPressure.text = weatherResponse.main?.pressure?.toString() ?: "N/A"
        binding.txtPressure.append(" hPa")

        binding.txtFeelsLike.text = viewModel.formatTemperature(weatherResponse.main?.feels_like)
    }
}