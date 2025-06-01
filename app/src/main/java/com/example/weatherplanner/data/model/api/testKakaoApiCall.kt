package com.example.weatherplanner.data.model.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log
import com.example.weatherplanner.data.model.repository.PlaceRepository

class PlaceViewModel : ViewModel() {
    private val repository = PlaceRepository()

    fun testKakaoApiCall(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val places = repository.searchMultipleCategories(lat, lon)
                Log.d("KakaoApiTest", "장소 수: ${places.size}")
                places.take(5).forEach {
                    Log.d("KakaoApiTest", "장소명: ${it.place_name}, 거리: ${it.distance}")
                }
            } catch (e: Exception) {
                Log.e("KakaoApiTest", "API 호출 실패", e)
            }
        }
    }
}

