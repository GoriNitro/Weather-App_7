package com.example.weather_app_7.data.network.repository

import com.example.weather_app_7.BuildConfig
import com.example.weather_app_7.data.network.apiservice.WeatherApiService

class WeatherRepository(val api: WeatherApiService) {
    fun getCurrentWeather(lat: Double, lng: Double, units: String) =
        api.getCurrentWeather(lat, lng, units,BuildConfig.WEATHER_API_KEY)

    fun getForecastWeather(lat: Double, lng: Double, units: String) =
        api.getForecastWeather(lat, lng, units,BuildConfig.WEATHER_API_KEY)
}