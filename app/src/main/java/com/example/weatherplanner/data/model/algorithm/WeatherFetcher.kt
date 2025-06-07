package com.example.weatherplanner.data.model.algorithm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherplanner.viewmodel.WeatherViewModel

@Composable
fun WeatherFetcher(
    lat: Double,
    lon: Double,
    weatherViewModel: WeatherViewModel = viewModel()
) {
    LaunchedEffect(lat, lon) {
        weatherViewModel.fetchWeather(lat, lon)
    }
}
