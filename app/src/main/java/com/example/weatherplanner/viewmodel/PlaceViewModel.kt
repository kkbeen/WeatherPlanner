package com.example.weatherplanner.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.WeatherApiResponse
import com.example.weatherplanner.data.model.repository.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceViewModel : ViewModel() {
    private val repository = PlaceRepository()

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> get() = _places

    fun loadRecommendedPlaces(lat: Double, lon: Double, weatherInfo: WeatherApiResponse?) {
        viewModelScope.launch {
            try {
                val places = repository.searchMultipleCategories(lat, lon)
                val filtered = if (weatherInfo != null) {
                    // 예시: 비가 오면 실내 카테고리만 추천
                    val isRain = weatherInfo.current.condition.text.contains("rain", ignoreCase = true)
                    places.filter { place ->
                        if (isRain) {
                            place.category_group_code == "FD6" || place.category_group_code == "CE7"
                        } else {
                            true
                        }
                    }
                } else places

                _places.value = filtered
            } catch (e: Exception) {
                Log.e("PlaceViewModel", "Error loading places", e)
            }
        }
    }
}