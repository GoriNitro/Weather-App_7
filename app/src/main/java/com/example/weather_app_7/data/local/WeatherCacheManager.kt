package com.example.weather_app_7.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.weather_app_7.data.network.model.CurrentResponseApi
import com.example.weather_app_7.data.network.model.ForecastResponseApi
import com.google.gson.Gson

object WeatherCacheManager {

    private const val PREF_NAME = "WeatherCache"
    private const val CURRENT_WEATHER_PREFIX = "current_weather_"
    private const val FORECAST_PREFIX = "forecast_"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveCurrentWeather(context: Context, cityName: String, data: CurrentResponseApi) {
        val gson = Gson()
        val json = gson.toJson(data)
        getPrefs(context).edit().putString(CURRENT_WEATHER_PREFIX + cityName, json).apply()
    }

    fun getCurrentWeather(context: Context, cityName: String): CurrentResponseApi? {
        val gson = Gson()
        val json = getPrefs(context).getString(CURRENT_WEATHER_PREFIX + cityName, null)
        return if (json != null) {
            gson.fromJson(json, CurrentResponseApi::class.java)
        } else {
            null
        }
    }

    fun saveForecast(context: Context, cityName: String, data: ForecastResponseApi) {
        val gson = Gson()
        val json = gson.toJson(data)
        getPrefs(context).edit().putString(FORECAST_PREFIX + cityName, json).apply()
    }

    fun getForecast(context: Context, cityName: String): ForecastResponseApi? {
        val gson = Gson()
        val json = getPrefs(context).getString(FORECAST_PREFIX + cityName, null)
        return if (json != null) {
            gson.fromJson(json, ForecastResponseApi::class.java)
        } else {
            null
        }
    }

}
