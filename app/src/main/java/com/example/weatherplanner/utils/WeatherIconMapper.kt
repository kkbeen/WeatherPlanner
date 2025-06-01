package com.example.weatherplanner.utils

import com.example.weatherplanner.R

fun getWeatherIconRes(condition: String): Int {
    return when (condition.lowercase()) {
        "sunny", "clear" -> R.drawable.ic_weather_sunny
        "partly cloudy" -> R.drawable.ic_weather_partly_cloudy
        "cloudy", "overcast" -> R.drawable.ic_weather_cloudy
        "mist", "fog" -> R.drawable.ic_weather_mist
        "light rain", "patchy rain possible" -> R.drawable.ic_weather_light_rain
        "moderate rain" -> R.drawable.ic_weather_moderate_rain
        "heavy rain" -> R.drawable.ic_weather_heavy_rain
        "snow", "light snow" -> R.drawable.ic_weather_snow
        "thundery outbreaks possible", "thunder", "thunderstorm" -> R.drawable.ic_weather_thundery
        else -> R.drawable.ic_weather_cloudy // 기본 아이콘
    }
}