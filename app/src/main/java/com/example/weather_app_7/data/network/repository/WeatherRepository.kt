package com.example.weather_app_7.data.network.repository

import android.graphics.Bitmap.Config
import com.example.weather_app_7.BuildConfig
import com.example.weather_app_7.data.network.ApiService
import com.google.gson.internal.GsonBuildConfig

class WeatherRepository(val api: ApiService) {
    fun getCurrentWeather(lat: Double, lng: Double, units: String) =
        api.getCurrentWeather(lat, lng, units,"70cb552311d5f973e4ce1f6a3426d9ba")
}