package com.figueroa.weatherapp.model

data class WeatherForecast(
    val current: CurrentX,
    val forecast: Forecast,
    val location: LocationX
)