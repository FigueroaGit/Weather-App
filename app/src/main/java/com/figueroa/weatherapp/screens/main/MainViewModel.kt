package com.figueroa.weatherapp.screens.main

import androidx.lifecycle.ViewModel
import com.figueroa.weatherapp.data.DataOrException
import com.figueroa.weatherapp.model.WeatherForecast
import com.figueroa.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    suspend fun getWeatherData(city: String): DataOrException<WeatherForecast, Boolean, Exception> {
        return repository.getWeather(cityQuery = city)
    }
}
