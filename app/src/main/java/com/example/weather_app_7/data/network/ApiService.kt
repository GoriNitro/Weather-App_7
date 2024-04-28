package com.example.weather_app_7.data.network

import com.example.weather_app_7.data.network.model.CurrentResponceApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") ApiKey: String
    ): Call<CurrentResponceApi>
}