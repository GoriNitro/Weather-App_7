package com.example.weather_app_7.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weather_app_7.data.network.ApiClient
import com.example.weather_app_7.data.network.ApiService
import com.example.weather_app_7.data.network.repository.WeatherRepository

class WeatherViewModel(val repository: WeatherRepository) : ViewModel() {
    constructor() : this(WeatherRepository(ApiClient().getClient().create(ApiService::class.java)))

    fun getCurrentWeather(lat: Double, lng: Double, units: String) =
        repository.getCurrentWeather(lat, lng, units)
}