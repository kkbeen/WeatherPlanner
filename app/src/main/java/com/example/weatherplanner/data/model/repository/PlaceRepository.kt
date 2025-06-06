package com.example.weatherplanner.data.model.repository

import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.network.RetrofitKakaoInstance

class PlaceRepository {
    suspend fun searchMultipleCategories(
        lat: Double,
        lon: Double
    ): List<Place> {
        val categories = listOf("FD6", "CE7", "CT1", "AT4") // 음식점, 카페, 문화시설, 관광지
        val allResults = mutableListOf<Place>()

        for (category in categories) {
            val result = RetrofitKakaoInstance.api.searchPlacesByCategory(
                apiKey = "KakaoAK 2b1ee159643f483302cc365fa09589c4",
                categoryCode = category,
                longitude = lon,
                latitude = lat
            )
            allResults.addAll(result.documents)
        }

        return allResults
    }
}
