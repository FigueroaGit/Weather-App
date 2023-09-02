package com.figueroa.weatherapp.network

import com.figueroa.weatherapp.model.Forecastday
import com.figueroa.weatherapp.model.WeatherForecast
import com.figueroa.weatherapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherAPI {

    @GET(value = "forecast.json")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("days") days: Int = 1,
        @Query("aqi") airQuality: String = "no",
        @Query("alerts") alerts: String = "no",
        @Query("key") key: String = Constants.API_KEY,
    ): WeatherForecast
}
