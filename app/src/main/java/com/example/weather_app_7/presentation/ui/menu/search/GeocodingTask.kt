@file:Suppress("DEPRECATION")

package com.example.weather_app_7.presentation.ui.menu.search

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.AsyncTask
import android.util.Log

@Suppress("DEPRECATION")
class GeocodingTask(private val context: Context, private val cityName: String, private val callback: (Double, Double) -> Unit) :
    AsyncTask<Void, Void, Pair<Double, Double>>() {

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Void?): Pair<Double, Double>? {
        val geocoder = Geocoder(context)
        val addresses: MutableList<Address>? = geocoder.getFromLocationName(cityName, 1)
        return if (addresses!!.isNotEmpty()) {
            Pair(addresses[0].latitude, addresses[0].longitude)
        } else {
            null
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: Pair<Double, Double>?) {
        super.onPostExecute(result)
        if (result != null) {
            callback.invoke(result.first, result.second)
        }
    }
}
