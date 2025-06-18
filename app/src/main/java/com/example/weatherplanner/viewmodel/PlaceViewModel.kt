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
        Log.d("PlaceViewModel", "loadRecommendedPlaces 호출: lat=$lat, lon=$lon, weatherInfo=$weatherInfo, userPrefs=$userPrefs")
        viewModelScope.launch {
            try {
                val places = repository.searchMultipleCategories(lat, lon)
                Log.d("PlaceViewModel", "카카오 API 장소 개수: ${places.size}")
                val currentHour = LocalTime.now().hour

                val ranked = places.map { place ->
                    PlaceScore(place, scorePlace(place, weatherInfo, userPrefs, currentHour))
                }.sortedByDescending { it.score }
                    .map { it.place }

                Log.d("PlaceViewModel", "추천 장소(랭킹 후) 개수: ${ranked.size}")
                _places.value = ranked
            } catch (e: Exception) {
                Log.e("PlaceViewModel", "추천 장소 로딩 실패", e)
            }
        }
    }

}
