package com.example.weather_app_7.data.network.model

import com.google.gson.annotations.SerializedName

data class ForecastResponseApi(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: Int,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("list")
    val list: List<WeatherEntry>,
    @SerializedName("city")
    val city: City
)

data class WeatherEntry(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("rain")
    val rain: Rain?,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("dt_txt")
    val dt_txt: String
)

data class Main(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feels_like: Double,
    @SerializedName("temp_min")
    val temp_min: Double,
    @SerializedName("temp_max")
    val temp_max: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("sea_level")
    val sea_level: Int,
    @SerializedName("grnd_level")
    val grnd_level: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("temp_kf")
    val temp_kf: Double
)

data class Weather(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

data class Clouds(
    @SerializedName("all")
    val all: Int
)

data class Wind(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("gust")
    val gust: Double
)

data class Rain(
    @SerializedName("3h")
    val `3h`: Double
)

data class Sys(
    @SerializedName("pod")
    val pod: String
)

data class City(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("country")
    val country: String,
    @SerializedName("population")
    val population: Int,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long
)

data class Coord(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)

