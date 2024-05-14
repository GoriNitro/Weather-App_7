package com.example.weather_app_7.presentation.ui.menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_app_7.R
import com.example.weather_app_7.data.local.WeatherCacheManager
import com.example.weather_app_7.data.network.model.CurrentResponseApi
import com.example.weather_app_7.data.network.model.ForecastResponseApi
import com.example.weather_app_7.databinding.FragmentMenuBinding
import com.example.weather_app_7.presentation.adapters.ForecastAdapter
import com.example.weather_app_7.presentation.ui.menu.search.GeocodingTask
import com.example.weather_app_7.presentation.ui.viewmodel.WeatherViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Suppress("DEPRECATION")
class MenuFragment : Fragment() {

    private val binding by lazy {
        FragmentMenuBinding.inflate(layoutInflater)
    }
    private val viewModel: WeatherViewModel by viewModels()

    private val calendar by lazy {
        Calendar.getInstance()
    }
    private val forecastAdapter by lazy {
        ForecastAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBarRv.visibility = View.VISIBLE
        val drawable = if (isNightShow()) R.drawable.night_bg else
            R.drawable.sunny_bg
        binding.imgWeather.setImageResource(drawable)
        binding.apply {
            val bundle = arguments
            val name = bundle?.getString("City")
            tvLoc.text = name
            btnSearch.setOnClickListener {
                val bundleto = Bundle().apply {
                    putString("name", name)
                }
                findNavController().navigate(R.id.searchCityFragment, bundleto)
            }
            //searching lat lon by city name
            val geocodingTask = name?.let {
                GeocodingTask(requireContext(), it) { lat, lon ->
                    loadWeatherData(lat, lon, "current", name)
                    loadWeatherData(lat, lon, "forecast", name)
                }
            }
            geocodingTask?.execute()
        }
    }

    private fun FragmentMenuBinding.loadWeatherData(
        lat: Double,
        lon: Double,
        type: String,
        cityName: String
    ) {
        val currentWeatherCache = WeatherCacheManager.getCurrentWeather(requireContext(), cityName)
        val forecastCache = WeatherCacheManager.getForecast(requireContext(), cityName)

        if (currentWeatherCache != null && type == "current") {
            updateCurrentWeatherUI(currentWeatherCache)
        } else if (forecastCache != null && type == "forecast") {
            updateForecastUI(forecastCache)
        } else {
            val weatherCallback = when (type) {
                "current" -> object : Callback<CurrentResponseApi> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<CurrentResponseApi>,
                        response: Response<CurrentResponseApi>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            data?.let {
                                tvTempMin.text =
                                    "Temp:\n ${it.main?.tempMin.toString()}" + "°"

                                val formattedDate = toTxt(it)

                                tvDay.text = formattedDate
                                WeatherCacheManager.saveCurrentWeather(requireContext(), cityName, it)
                            }
                        }
                    }

                    override fun onFailure(call: Call<CurrentResponseApi>, t: Throwable) {
                        Toast.makeText(requireContext(), "t.message", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                else -> object : Callback<ForecastResponseApi> {
                    override fun onResponse(
                        call: Call<ForecastResponseApi>,
                        response: Response<ForecastResponseApi>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            data?.let {
                                forecastAdapter.differ.submitList(it.list)
                                rvPrognoz.layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                                rvPrognoz.adapter = forecastAdapter
                                progressBarRv.visibility = View.GONE
                                WeatherCacheManager.saveForecast(requireContext(), cityName, it)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ForecastResponseApi>, t: Throwable) {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось загрузить данные",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            when (type) {
                "current" -> viewModel.getCurrentWeather(lat, lon, "metric")
                    .enqueue(weatherCallback as Callback<CurrentResponseApi>)

                "forecast" -> viewModel.getForecastWeather(lat, lon, "metric")
                    .enqueue(weatherCallback as Callback<ForecastResponseApi>)
            }

        }
    }


    //format date and time
    private fun toTxt(it: CurrentResponseApi): String {
        val timestamp: Int = it.dt!!

        val date = Date(timestamp.toLong() * 1000)

        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return sdf.format(date)
    }


    //checking if it is night or not
    private fun isNightShow(): Boolean {
        return calendar.get(Calendar.HOUR_OF_DAY) >= 18
    }


    @SuppressLint("SetTextI18n")
    private fun updateCurrentWeatherUI(data: CurrentResponseApi) {

        binding.apply {

            tvTempMin.text = "Temp:\n ${data.main?.tempMin.toString()}°"
            val formattedDate = toTxt(data)
            tvDay.text = formattedDate

            val drawable = if (isNightShow()) R.drawable.night_bg else R.drawable.sunny_bg
            imgWeather.setImageResource(drawable)

        }
    }

    private fun updateForecastUI(data: ForecastResponseApi) {
        binding.apply {
            forecastAdapter.differ.submitList(data.list)

            rvPrognoz.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            rvPrognoz.adapter = forecastAdapter

            progressBarRv.visibility = View.GONE

        }
    }
}