package com.example.weather_app_7.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weather_app_7.data.network.model.WeatherEntry
import com.example.weather_app_7.databinding.ItemWeatherBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    private lateinit var binding: ItemWeatherBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemWeatherBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat", "DiscouragedApi")
    override fun onBindViewHolder(holder: ForecastAdapter.ViewHolder, position: Int) {
        val binding = ItemWeatherBinding.bind(holder.itemView)
        val date =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(differ.currentList[position].dt_txt)
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        val dayOfWeek = when (calendar[Calendar.DAY_OF_WEEK]) {
            1 -> "Пн"
            2 -> "Вт"
            3 -> "Ср"
            4 -> "Чт"
            5 -> "Пт"
            6 -> "Сб"
            7 -> "Вс"
            else -> "-"
        }
        binding.tvTime.text = dayOfWeek
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val amPm = if (hour < 12) " am" else " pm"
        val hour12 = calendar[Calendar.HOUR]
        binding.hourOfDay.text = "$hour12:00$amPm"
        binding.tvPergentage.text = differ.currentList[position].main.temp.let {
            Math.round(it)
        }.toString() + "°"
        val icon = when (differ.currentList[position].weather[0].icon) {
            "01d", "0n" -> "sunny"
            "02d", "02n" -> "cloudy_sunny"
            "03d", "03n" -> "cloudy_sunny"
            "04d", "02=4n" -> "cloudy"
            "09d", "09n" -> "rainy"
            "10d", "10n" -> "rainy"
            "11d", "11n" -> "storm"
            "13d", "13n" -> "snowy"
            "50d", "50n" -> "windy"
            else -> "sunny"
        }
        val drawableResourceId: Int = binding.root.resources.getIdentifier(
            icon,
            "drawable", binding.root.context.packageName
        )
        binding.imgWeather.load(drawableResourceId)
        binding.tvWind.text = differ.currentList[position].wind.speed.toInt().toString() + "м/c"
    }

    override fun getItemCount() = differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

    }

    private val differCallBack = object : DiffUtil.ItemCallback<WeatherEntry>() {
        override fun areItemsTheSame(
            oldItem: WeatherEntry,
            newItem: WeatherEntry
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: WeatherEntry,
            newItem: WeatherEntry
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)
}