package com.example.weatherplanner.utils

fun translateWeatherCondition(condition: String): String {
    return when (condition.lowercase()) {
        "sunny" -> "맑음"
        "clear" -> "맑음"
        "partly cloudy" -> "부분 흐림"
        "cloudy" -> "흐림"
        "overcast" -> "흐림"
        "mist" -> "안개"
        "patchy rain possible" -> "약간의 비 가능성"
        "light rain" -> "가벼운 비"
        "moderate rain" -> "보통 비"
        "heavy rain" -> "강한 비"
        "thundery outbreaks possible" -> "천둥 가능성"
        "snow" -> "눈"
        "light snow" -> "가벼운 눈"
        "fog" -> "안개"
        else -> condition
    }
}