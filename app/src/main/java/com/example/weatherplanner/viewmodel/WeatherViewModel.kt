package com.example.weatherplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherplanner.data.model.WeatherApiResponse
import com.example.weatherplanner.data.model.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()

    private val _weather = MutableStateFlow<WeatherApiResponse?>(null)
    val weather: StateFlow<WeatherApiResponse?> = _weather

    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val weatherResponse = repository.getWeather(lat, lon)
                weatherResponse?.location?.lat = lat
                weatherResponse?.location?.lon = lon
                _weather.value = weatherResponse
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}