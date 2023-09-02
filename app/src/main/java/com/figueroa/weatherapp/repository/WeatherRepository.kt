package com.figueroa.weatherapp.repository

import android.util.Log
import com.figueroa.weatherapp.data.DataOrException
import com.figueroa.weatherapp.model.Forecastday
import com.figueroa.weatherapp.model.WeatherForecast
import com.figueroa.weatherapp.network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val API: WeatherAPI) {

    suspend fun getWeather(cityQuery: String): DataOrException<WeatherForecast, Boolean, Exception> {
        val response = try {
            API.getWeather(query = cityQuery)
        } catch (e: Exception) {
            Log.d("REX", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = response)
    }
}
