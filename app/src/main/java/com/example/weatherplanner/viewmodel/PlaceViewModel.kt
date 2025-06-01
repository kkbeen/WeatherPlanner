package com.example.weatherplanner.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.WeatherApiResponse
import com.example.weatherplanner.data.model.algorithm.PlaceScore
import com.example.weatherplanner.data.model.algorithm.UserPreferences
import com.example.weatherplanner.data.model.algorithm.scorePlace
import com.example.weatherplanner.data.model.repository.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime

class PlaceViewModel : ViewModel() {
    private val repository = PlaceRepository()

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> get() = _places

    fun loadRecommendedPlaces(
        lat: Double,
        lon: Double,
        weatherInfo: WeatherApiResponse?,
        userPrefs: UserPreferences
    ) {
        viewModelScope.launch {
            try {
                val places = repository.searchMultipleCategories(lat, lon)
                val currentHour = LocalTime.now().hour

                val ranked = places.map { place ->
                    PlaceScore(place, scorePlace(place, weatherInfo, userPrefs, currentHour))
                }.sortedByDescending { it.score }
                    .map { it.place }

                _places.value = ranked
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }

}
