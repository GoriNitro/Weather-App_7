package com.example.weather_app_7.presentation.ui.menu

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.weather_app_7.R
import com.example.weather_app_7.data.network.model.CurrentResponceApi
import com.example.weather_app_7.databinding.FragmentMenuBinding
import com.example.weather_app_7.presentation.ui.viewmodel.WeatherViewModel
import retrofit2.Call
import retrofit2.Response
import java.util.Calendar

class MenuFragment : Fragment() {
    private val binding by lazy {
        FragmentMenuBinding.inflate(layoutInflater)
    }
    private val viewModel: WeatherViewModel by viewModels()
    private val calendar by lazy {
        Calendar.getInstance()
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
        binding.apply {
            var lot = 51.50
            var lon = -0.12
            var name = "Bishkek"

            tvLoc.text = name
            tvLocation.text = name
            viewModel.getCurrentWeather(lot, lon, "metric")
                .enqueue(object : retrofit2.Callback<CurrentResponceApi> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<CurrentResponceApi>,
                        response: Response<CurrentResponceApi>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            data?.let {
                                tvTemp.text = it.main?.temp.toString() + "°"
                                tvDay.text = it.weather?.get(0)?.description
                                val drawable = if (isNightShow()) R.drawable.night_bg else
                                    R.drawable.sunny_bg
                                imgWeather.setImageResource(drawable)
                            }
                        }
                    }

                    override fun onFailure(call: Call<CurrentResponceApi>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                })

        }
    }

    private fun isNightShow(): Boolean {
        return calendar.get(Calendar.HOUR_OF_DAY) >= 18
    }
}