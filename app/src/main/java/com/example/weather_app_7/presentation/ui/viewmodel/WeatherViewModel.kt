package com.example.weather_app_7.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weather_app_7.BuildConfig
import com.example.weather_app_7.data.network.ApiClient
import com.example.weather_app_7.data.network.apiservice.WeatherApiService
import com.example.weather_app_7.data.network.repository.WeatherRepository

class WeatherViewModel(val repository: WeatherRepository) : ViewModel() {
    constructor() : this(WeatherRepository(ApiClient(BuildConfig.WEATHER_BASE_URL).getClient().create(WeatherApiService::class.java)))

    fun getCurrentWeather(lat: Double, lng: Double, units: String) =
        repository.getCurrentWeather(lat, lng, units)

    fun getForecastWeather(lat: Double, lng: Double, units: String) =
        repository.getForecastWeather(lat, lng, units)

}