package com.example.weatherplanner.data.model.algorithm

import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.WeatherApiResponse

data class PlaceScore(
    val place: Place,
    val score: Double
)

fun scorePlace(place: Place, weatherInfo: WeatherApiResponse?, userPrefs: UserPreferences, currentHour: Int): Double {
    var score = 0.0

    val isRain = weatherInfo?.current?.condition?.text?.contains("rain", ignoreCase = true) ?: false
    val distance = place.distance.toDoubleOrNull() ?: 10000.0

    // 거리 가중치
    score += (10000 - distance) / 2000

    // 날씨 기반 카테고리 가중치
    if (isRain && (place.category_group_code == "FD6" || place.category_group_code == "CE7")) {
        score += 5.0
    }

    // 사용자 선호 카테고리 가중치
    if (place.category_group_code in userPrefs.preferredCategories) {
        score += 3.0
    }

    // 시간대 가중치
    if (currentHour in 14..17 && place.category_group_code == "CE7") {
        score += 2.0
    }

    return score
}
