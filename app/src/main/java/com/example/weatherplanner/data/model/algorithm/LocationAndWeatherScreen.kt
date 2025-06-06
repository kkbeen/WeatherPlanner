package com.example.weatherplanner.data.model.algorithm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun LocationAndWeatherScreen() {
    var userLat by remember { mutableStateOf<Double?>(null) }
    var userLon by remember { mutableStateOf<Double?>(null) }

    LocationFetcher { lat, lon ->
        userLat = lat
        userLon = lon
    }

    if (userLat != null && userLon != null) {
        WeatherFetcher(lat = userLat!!, lon = userLon!!)
    }
}
