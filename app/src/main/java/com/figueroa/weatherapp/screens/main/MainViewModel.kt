package com.figueroa.weatherapp.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.figueroa.weatherapp.data.DataOrException
import com.figueroa.weatherapp.model.Forecastday
import com.figueroa.weatherapp.model.WeatherForecast
import com.figueroa.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    suspend fun getWeatherData(city: String) : DataOrException<WeatherForecast, Boolean, Exception> {
        return repository.getWeather(cityQuery = city)
    }

}
