package com.example.weatherplanner.data.model.algorithm

import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.WeatherApiResponse

fun rankPlaces(
    places: List<Place>,
    weatherInfo: WeatherApiResponse?,
    userPrefs: UserPreferences,
    currentHour: Int
): List<Place> {
    return places.map { place ->
        PlaceScore(place, scorePlace(place, weatherInfo, userPrefs, currentHour))
    }
        .sortedByDescending { it.score }
        .map { it.place }
}