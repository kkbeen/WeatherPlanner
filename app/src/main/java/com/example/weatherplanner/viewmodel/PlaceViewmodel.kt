package com.example.weatherplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.repository.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceViewModel : ViewModel() {
    private val repository = PlaceRepository()

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places

    fun loadNearbyPlaces(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                _places.value = repository.searchMultipleCategories(lat, lon)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
