package com.example.weatherplanner.data.model.repository

import com.example.weatherplanner.data.model.WeatherApiResponse
import com.example.weatherplanner.data.model.network.RetrofitInstance

class WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): WeatherApiResponse {
        val location = "$lat,$lon"
        return RetrofitInstance.api.getForecastWeather(
            apiKey = "199d649e76a2409b92a160614252805",
            location = location
        )
    }
}

